package lesson8.gameengine;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Движок игры, за основу взят движок из Урока 4 (возможны унаследованные "недочеты")
 */
public class TicTacToe {

    public static Random rand = new Random();

    //игровое поле игры
    @Getter
    private FieldCell[][] map;
    //размер игрового поля игры (по-умолчанию == 3)
    @Getter
    private int mapSize = 3;
    //кол-во последовательных ячеек, чтобы выиграть (по-умолчанию == 3)
    @Getter
    private int dotsToWin = 3;

    private JPanel gameField;

    //вспомогательная переменная считающая сколько ходов осталось
    int turnsLeft;

    public TicTacToe(int mapSize, int dotsToWin) {
        this.mapSize = mapSize;
        this.dotsToWin = dotsToWin;
    }

    /**
     * Функция инициализации игры: подготавливаем поле, выбираем случайно, кто первый будет ходить (AI|HUMAN)
     */
    public void initGame() {
        //инициализируем игровое поле и заполним его DOT_EMPTY
        map = new FieldCell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = new FieldCell(j, i);
                map[i][j].addActionListener(action -> {
                    FieldCell actionCell = (FieldCell) action.getSource();
                    if (actionCell.getAccupation() == Players.EMPTY) {
                        this.humanTurn(actionCell.getXPosition(), actionCell.getYPosition());

                        if (this.isHumanWon()) {
                            gameOverDialog("Игрок победил!!!");
                        } else if (this.isDrawnGame()) {
                            gameOverDialog("Ничья!!!");
                        } else {
                            this.aiTurn();
                            if (this.isAiWon()) {
                                gameOverDialog("AI победил!!!");
                            } else if (this.isDrawnGame()) {
                                gameOverDialog("Ничья!!!");
                            }
                        }
                    }

                });
            }
        }
        //установим сколько ходов осталось, будем использовать для хода AI
        turnsLeft = mapSize * mapSize;
    }

    private void gameOverDialog(String message) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Игра окончена");
        dialog.setLocationRelativeTo(this.gameField);
        dialog.setLayout(new FlowLayout());
        dialog.add(new JLabel(message));
        dialog.setSize(250, 100);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                gameField.setVisible(false);
            }
        });
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    public JPanel getGameFiled() {
        this.gameField = new JPanel();
        this.gameField.setLayout(new GridLayout(this.getMapSize(), this.getMapSize(), 1, 1));
        FieldCell[][] map = this.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                this.gameField.add(map[i][j]);
            }
        }

        return this.gameField;
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
            for (FieldCell dot : map[i]) {
                String dotText = dot.getAccupation() == Players.EMPTY ? "_" :
                    dot.getAccupation() == Players.HUMAN ? "X" : "O";
                System.out.print(dotText + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Функция хода AI
     */
    public void aiTurn() {
        //сначала попробуем высчитать куда походить просматривая возможные варианты выигрыша Игрока и AI
        //для этого поочередно переберем пустые ячейки и ставя туда DOT_HUMAN или DOT_AI проверять на выигрыш
        //выигрыш приоритетнее блокировки игрока, поэтому сначал проверяем с DOT_AI, при этом
        //если найдем выриант выигрыша Игрока, то запомним его
        int x = -1, y = -1;
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                //если клетка еще не занята, то проверим ее на выигрыш AI
                if (map[i][j].getAccupation() == Players.EMPTY) {
                    map[i][j].setAccupation(Players.AI);
                    if (isSymbolWins(Players.AI)) {
                        //если AI выигрывает, то так и оставим
                        finishTurn();
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        return;
                    }
                    //AI не выигрывает, проверим не выиграет ли человек
                    map[i][j].setAccupation(Players.HUMAN);
                    if (isSymbolWins(Players.HUMAN)) {
                        //если Игрок выигрывает, то запомним эти координаты
                        y = i;
                        x = j;
                    }
                    //вернем DOT_EMPTY
                    map[i][j].setAccupation(Players.EMPTY);
                }
            }
        }

        //если нашли что надо блокировать игрока
        if (x != -1 && y != -1) {
            map[y][x].setAccupation(Players.AI);
            finishTurn();
            System.out.println("Компьютер походил в точку " + (x + 1) + " " + (y + 1));
            return;
        }

        //для хода AI случайно выберем одну из  оставшихся клеток
        //для этого определим случайный последовательный номер оставшихся клеток
        int nextRandomDotNumber = rand.nextInt(turnsLeft) + 1;
        //отсчитаем нужную клетку
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j].getAccupation() == Players.EMPTY) {
                    nextRandomDotNumber--;
                    if (nextRandomDotNumber == 0) {
                        //клетку нашли, ходим
                        map[i][j].setAccupation(Players.AI);
                        System.out.println("Компьютер походил в точку " + (j + 1) + " " + (i + 1));
                        finishTurn();
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
    public void humanTurn(int x, int y) {
        if (!isTurnValid(x, y))
            throw new IllegalStateException("Что-то не так с игровым полем!!!");

        map[y][x].setAccupation(Players.HUMAN);
        finishTurn();
    }

    private void finishTurn() {
        turnsLeft--;
        printMap();
    }

    /**
     * Функция проверки на выигрыш переданного символа (крестика или нолика, но функцию не будем ограничивать только
     * этими символами, пусть будет универсальная)
     *
     * @param symbol - символ для проверки на выигрыш
     */
    private boolean isSymbolWins(Players symbol) {
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

    private boolean checkWinOnSquare(int offsetByX, int offsetByY, Players symbol) {
        //проверим диагонали
        //переменная для диагонали слева-сверху в справа-снизу
        boolean isWinFromTopToBottom = true;
        //переменная для диагонали слева-сверху в справа-снизу
        boolean isWinFromBottomToTop = true;
        for (int i = 0; i < dotsToWin; i++) {
            isWinFromTopToBottom &= (map[offsetByX + i][offsetByY + i].getAccupation() == symbol);
            isWinFromBottomToTop &= (map[offsetByX + dotsToWin - i - 1][offsetByY + i].getAccupation() == symbol);
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
                isColumnWin &= (map[offsetByX + col][offsetByY + row].getAccupation() == symbol);
                isRowWin &= (map[offsetByX + row][offsetByY + col].getAccupation() == symbol);
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
    public boolean isDrawnGame() {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (map[i][j].getAccupation() == Players.EMPTY) return false;
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
            if (map[y][x].getAccupation() == Players.EMPTY) return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректные координаты: координаты выходят за рамки поля.");
            return false;
        }

        System.out.println("Клетка (" + x + "," + y + ") уже занята.");
        return false;
    }

    public boolean isHumanWon() {
        return this.isSymbolWins(Players.HUMAN);
    }

    public boolean isAiWon() {
        return this.isSymbolWins(Players.AI);
    }
}
