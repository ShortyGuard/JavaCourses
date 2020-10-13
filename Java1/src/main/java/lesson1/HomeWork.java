package lesson1;

import java.util.Arrays;

public class HomeWork {
    /**
     * 1. Создать пустой проект в IntelliJ IDEA и прописать метод main();
     */
    public static void main(String[] args) {
        //2. Создать переменные всех пройденных типов данных, и инициализировать их значения;
        byte b = 127;
        short s = 32767;
        int i = 2000000000;
        long l = 2000000000000000000L;

        float f = 0.1f;
        double d = 0.1;

        char c = 'a';

        //как бонус ссылочный тип строки
        String string = "Hello";

        //вызовы методов домашнего задания
        System.out.println("Выражение 'a * (b + (c / d)', expression(1, 2, 3, 4): " + expression(1, 2, 3, 4));
        System.out.println("isSumBetweenTenAndTwenty(3, 11)): " + isSumBetweenTenAndTwenty(3, 11));
        System.out.println("isSumBetweenTenAndTwenty(13, 11): " + isSumBetweenTenAndTwenty(13, 11));
        System.out.print("printSignOfNumber(5): ");
        printSignOfNumber(5);
        System.out.print("printSignOfNumber(-5): ");
        printSignOfNumber(-5);
        System.out.println("isNumberNegative(5): " + isNumberNegative(5));
        System.out.println("isNumberNegative(-5): " + isNumberNegative(-5));
        printHello("Михаил");
        printIsLeapYear(1934);
        printIsLeapYear(2020);
        printIsLeapYear(4);
        printIsLeapYear(100);
        printIsLeapYear(400);
    }

    /**
     * 3. Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат,
     * где a, b, c, d – входные параметры этого метода;
     * (для точности примем, что входные параметры имеют тип double)
     */
    public static double expression(double a, double b, double c, double d) {
        return a * (b + (c / d));
    }

    /**
     * 4. Написать метод, принимающий на вход два числа, и проверяющий,
     * что их сумма лежит в пределах от 10 до 20(включительно), если да – вернуть true, в противном случае – false;
     * (для точности примем, что входные параметры целочисленные)
     */
    public static boolean isSumBetweenTenAndTwenty(int a, int b) {
        int sum = a + b;
        return (sum > 10 && sum <= 20);
    }

    /**
     * 5. Написать метод, которому в качестве параметра передается целое число,
     * метод должен напечатать в консоль положительное ли число передали, или отрицательное;
     * Замечание: ноль считаем положительным числом.
     */
    public static void printSignOfNumber(int number) {
        if (number < 0) {
            System.out.println(number + " is negative number");
        } else {
            System.out.println(number + " is positive number");
        }
    }

    /**
     * 6. Написать метод, которому в качестве параметра передается целое число,
     * метод должен вернуть true, если число отрицательное;
     */
    public static boolean isNumberNegative(int number) {
        return number < 0;
    }

    /**
     * 7. Написать метод, которому в качестве параметра передается строка, обозначающая имя,
     * метод должен вывести в консоль сообщение «Привет, указанное_имя!»;
     */
    public static void printHello(String name) {
        System.out.println("Привет, " + name + "!");
    }

    /**
     * 8. * Написать метод, который определяет является ли год високосным, и выводит сообщение в консоль.
     * Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
     * (для точности примем что год передают параметром как целое число, а если передано отрицательное число,
     * то выводим что год не может быть отрицательным)
     */
    public static void printIsLeapYear(int year) {
        if (year >= 0) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                System.out.println(year + " is a leap year!");
            } else {
                System.out.println(year + " is not a leap year!");
            }
        } else {
            System.out.println("Year cannot be negative! " + year + " is not an year!");
        }

    }
}
