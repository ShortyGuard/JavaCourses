package lesson3;

import java.util.Random;
import java.util.Scanner;

public class HomeWorkLesson3 {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        HomeWorkLesson3 homeWorkLesson3 = new HomeWorkLesson3();

        //задание 1
        homeWorkLesson3.guessNumber();

        //задание 2
        homeWorkLesson3.guessWord();

    }

    /**
     * 1. Написать программу, которая загадывает случайное число от 0 до 9 и пользователю дается 3 попытки
     * угадать это число.
     * При каждой попытке компьютер должен сообщить, больше ли указанное пользователем число, чем загаданное,
     * или меньше.
     * После победы или проигрыша выводится запрос – «Повторить игру еще раз? 1 – да / 0 – нет»(1 – повторить, 0 – нет).
     */
    public void guessNumber() {
        System.out.println("Задание 1. Угадай число");

        Random rand = new Random();
        int numberToGuess;
        int userNumber = 0;
        //введем переменную для определения необходимости повторов игры: 1 - играть, 0 - закончить
        int stillPlaying = 1;

        while (stillPlaying == 1) {
            //загадаем число
            numberToGuess = rand.nextInt(10);
            System.out.println("Угадайте число от 0 до 9");
            //дадим три попытки угадать число
            for (int i = 0; i < 3; i++) {
                userNumber = getNumberFromScanner("Чтобы угадать введите число от 0 до 9", 0, 9);
                if (userNumber == numberToGuess) {
                    System.out.println("Вы угадали!");
                    break;
                } else if (numberToGuess < userNumber) {
                    System.out.println("Введенное вами число больше загаданного");
                } else {
                    System.out.println("Введенное вами число меньше загаданного");
                }
            }
            if (numberToGuess != userNumber) {
                System.out.println("Вы проиграли!");
            }

            stillPlaying = getNumberFromScanner("Повторить игру еще раз? 1 – да / 0 – нет", 0, 1);
        }
    }

    //вспомогательная функция получения нужного числа
    private int getNumberFromScanner(String message, int min, int max) {
        int x;
        do {
            System.out.println(message);
            x = sc.nextInt();
        } while (x < min || x > max);

        return x;
    }

    /**
     * 2. * Создать массив из слов
     * String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
     * "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea",
     * "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"}.
     * <p>
     * При запуске программы компьютер загадывает слово, запрашивает ответ у пользователя, сравнивает его с загаданным
     * словом и сообщает, правильно ли ответил пользователь.
     * Если слово не угадано, компьютер показывает буквы, которые стоят на своих местах.
     * apple – загаданное
     * apricot - ответ игрока
     * ap############# (15 символов, чтобы пользователь не мог узнать длину слова)
     * Для сравнения двух слов посимвольно можно пользоваться:
     * String str = "apple";
     * char a = str.charAt(0); - метод, вернет char, который стоит в слове str на первой позиции
     * Играем до тех пор, пока игрок не отгадает слово.
     * Используем только маленькие буквы
     */
    public void guessWord() {
        System.out.println("Задание 2");

        Random rand = new Random();
        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
                "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea",
                "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

        //переменная индекса загаданного слова. Загадаем слово
        int wordIndex = rand.nextInt(words.length);
        //переменная ввденного пользователем слова
        String userWord;

        System.out.println("Угадайте слово (на английском)");
        //играем пока игрок не угадает слово
        while (true) {
            //ждем когда пользователь введет слово
            userWord = sc.nextLine();
            //если ввели пустую строку или только пробелы, то не будем ничего делать, а подождем следующего ввода
            if (userWord.trim().length() == 0) {
                continue;
            }
            //если слов угадано, то прекращаем игру
            if (words[wordIndex].equals(userWord)) {
                System.out.println("Вы угадали слово: " + words[wordIndex]);
                break;
            } else {
                System.out.println("Вы не угадали. Это не слово: " + userWord);

                //массив для угаданных символов
                char[] guessedChars = new char[15];
                for (int i = 0; i < guessedChars.length; i++) {
                    if (i < words[wordIndex].length() && i < userWord.length() && words[wordIndex].charAt(i) == userWord.charAt(i)) {
                        guessedChars[i] = words[wordIndex].charAt(i);
                    } else {
                        guessedChars[i] = '#';
                    }
                }
                System.out.println("Угаданные буквы: " + new String(guessedChars));
                System.out.println("Попробуйте угадать дальше.");
            }
        }

    }
}
