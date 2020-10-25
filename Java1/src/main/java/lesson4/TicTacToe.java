package lesson4;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static Scanner sc = new Scanner(System.in);
    public static Random rand = new Random();

    //консанта пустой ячейки
    public static final char DOT_EMPTY = '_';
    //константа ячеки AI - "нолик"
    public static final char DOT_AI = 'O';
    //константа ячейки HUMAN - крестик
    public static final char DOT_HUMAN = 'X';

    //игровое поле игры
    private char[][] map;
    //размер игрового поля игры (по-умолчанию == 3)
    private int mapSize = 3;
    //кол-во последовательных ячеек, чтобы выиграть (по-умолчанию == 3)
    private int dotsToWin = 3;

    //кто сейчас ходит (по-умолчанию HUMAN)
    private boolean isHumanTurns = true;

    //вспомогательная переменная считающая сколько ходов осталось
    int turnsLeft;

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe(6, 4);

        ticTacToe.playGame();
    }

    public TicTacToe(int mapSize, int dotsToWin) {
        this.mapSize = mapSize;
        this.dotsToWin = dotsToWin;
    }

    /**
     * Функция выполения шагов игры
     */
    public void playGame() {
        //инициализируем начальное состояние игры
        initGame();
        System.out.println("Сыграем в игру TicTacToe (крестики-нолики) на поле " + mapSize + "х" + mapSize);
        System.out.println("Чтобы выиграть необходимо поставить в линию свои символы длинной " + dotsToWin);
        System.out.println("Символ игрока: " + DOT_HUMAN + ", символ Искуственного Интелекта: " + DOT_AI);
        System.out.println("Первым ходит: " + (isHumanTurns ? "Игрок" : "Искуственный Интеллект"));

        //основной цикл игры, играем пока AI или HUMAN не выиграют или не будет ничьей
        while (true) {
            //покжем поле
            printMap();
            if (isHumanTurns) {
                humanTurn();
                if (isSymbolWins(DOT_HUMAN)) {
                    System.out.println("Победил человек");
                    printMap();
                    break;
                }
            } else {
                aiTurn();
                if (isSymbolWins(DOT_AI)) {
                    System.out.println("Победил Искуственный Интеллект");
                    printMap();
                    break;
                }
            }
            if (isDrawnGame()) {
                System.out.println("Ничья");
                printMap();
                break;
            }
            isHumanTurns = !isHumanTurns;
            turnsLeft--;
        }

        System.out.println("Игра закончена");
    }

    /**
     * Функция инициализации игры: подготавливаем поле, выбираем случайно, кто первый будет ходить (AI|HUMAN)
     */
    private void initGame() {
        //инициализируем игровое поле и заполним его DOT_EMPTY
        map = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
        //установим сколько ходов осталось, будем использовать для хода AI
        turnsLeft = mapSize * mapSize;
        //случайно определеим кто будет первым ходить
        isHumanTurns = rand.nextBoolean();
    }

    /**
     * Функция печати игрового поля
     */
    private void printMap() {
        for (int i = 0; i <= mapSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < mapSize; i++) {
            System.out.print((i + 1) + "|");
            for (char dot : map[i]) {
                System.out.print(dot + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Функция хода AI
     */
    private void aiTurn() {
        //сначала попробуем высчитать куда походить просматривая возможные варианты выигрыша Игрока и AI
        //для этого поочередно переберем пустые ячейки и ставя туда DOT_HUMAN или DOT_AI проверять на выигрыш
        //выигрыш приоритетнее блокировки игрока, поэтому сначал проверяем с DOT_AI, при этом
        //если найдем выриант выигрыша Игрока, то запомним его
        int x = -1, y = -1;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                //если клетка еще не занята, то проверим ее на выигрыш AI
                if (map[i][j] == DOT_EMPTY) {
                    map[i][j] = DOT_AI;
                    if (isSymbolWins(DOT_AI)) {
                        //если AI выигрывает, то так и оставим
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        return;
                    }
                    //AI не выигрывает, проверим не выиграет ли человек
                    map[i][j] = DOT_HUMAN;
                    if (isSymbolWins(DOT_HUMAN)) {
                        //если Игрок выигрывает, то запомним эти координаты
                        y = i;
                        x = j;
                    }
                    //вернем DOT_EMPTY
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        //если нашли что надо блокировать игрока
        if (x != -1 && y != -1) {
            map[y][x] = DOT_AI;
            System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
            return;
        }

        //для хода AI случайно выберем одну из  оставшихся клеток
        //для этого определим случайный последовательный номер оставшихся клеток
        int nextRandomDotNumber = rand.nextInt(turnsLeft) + 1;
        //отсчитаем нужную клетку
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    nextRandomDotNumber--;
                    if (nextRandomDotNumber == 0) {
                        //клетку нашли, ходим
                        map[i][j] = DOT_AI;
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        return;
                    }
                }
            }
        }

        //тут что-то пошло не так
        throw new RuntimeException("Что-то пошло не так. В программе ошибка.");
    }

    /**
     * Функция хода человека
     */
    private void humanTurn() {
        int x = -1, y = -1;
        do {
            System.out.println("Введите координаты в формате X Y (размер поля: " + mapSize + "x" + mapSize + ")");
            try {
                x = sc.nextInt() - 1;
                y = sc.nextInt() - 1;
            } catch (Exception e) {
                System.out.println("Некорректные координаты.");
                sc.nextLine();
            }
        } while (!isTurnValid(x, y));
        map[y][x] = DOT_HUMAN;
    }

    /**
     * Функция проверки на выигрыш переданного символа (крестика или нолика, но функцию не будем ограничивать только
     * этими символами, пусть будет универсальная)
     *
     * @param symbol - символ для проверки на выигрыш
     */
    private boolean isSymbolWins(char symbol) {
        //определяем есть ли выигрыш в каждой клетке [dotsToWin X dotsToWin]
        for (int col = 0; col <= mapSize - dotsToWin; col++) {
            for (int row = 0; row <= mapSize - dotsToWin; row++) {
                //проверим клетку со смещением [col;row] на выигрыш
                if (checkWinOnSquare(col, row, symbol)) return true;
            }
        }
        // Если все квадраты проверены, а выигрыша нет, то вернем false
        return false;
    }

    private boolean checkWinOnSquare(int offsetByX, int offsetByY, char symbol) {
        //проверим диагонали
        //переменная для диагонали слева-сверху в справа-снизу
        boolean isWinFromTopToBottom = true;
        //переменная для диагонали слева-сверху в справа-снизу
        boolean isWinFromBottomToTop = true;
        for (int i = 0; i < dotsToWin; i++) {
            isWinFromTopToBottom &= (map[offsetByX + i][offsetByY + i] == symbol);
            isWinFromBottomToTop &= (map[offsetByX + dotsToWin - i - 1][offsetByY + i] == symbol);
        }
        //если хоть одна из проверок осталась true
        if (isWinFromBottomToTop || isWinFromTopToBottom) return true;

        //если не выиграли, то проверим столбцы и колонки
        //теперь проверим горизонтальные и вертикальные линии
        boolean isColumnWin, isRowWin;
        for (int col = 0; col < dotsToWin; col++) {
            isColumnWin = true;
            isRowWin = true;
            for (int row = 0; row < dotsToWin; row++) {
                isColumnWin &= (map[offsetByX + col][offsetByY + row] == symbol);
                isRowWin &= (map[offsetByX + row][offsetByY + col] == symbol);
            }

            //если нашли колонку или строку с выигрышем, то вернем true
            if (isColumnWin || isRowWin) return true;
        }

        //все проверки не принесли выигрыша, вернем false
        return false;
    }

    /**
     * Функция проверки, что игра свелась в ничью
     *
     * @return ture - если все ячейки не равны символу пустоты DOT_EMPTY
     */
    private boolean isDrawnGame() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j] == DOT_EMPTY) return false;
            }
        }
        return true;
    }

    /**
     * Функция проверки корректности хода по символу
     *
     * @param x - координата колонки в игровом поле
     * @param y - координата строки в игровом поле
     * @return - true, если в координатах (x,y) стоит символ пустоты DOT_EMPTY, иначе - false.
     */
    private boolean isTurnValid(int x, int y) {
        try {
            if (map[y][x] == DOT_EMPTY) return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректные координаты: координаты выходят за рамки поля.");
            return false;
        }

        System.out.println("Клетка (" + x + "," + y + ") уже занята.");
        return false;
    }
}
