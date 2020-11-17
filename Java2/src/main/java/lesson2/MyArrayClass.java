package lesson2;

import java.util.Arrays;

/**
 * Домашнее задание Урока 2 курма Java. Уровень 2:
 * 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4.
 * При подаче массива другого размера необходимо бросить исключение MyArraySizeException.
 * 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать.
 * Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
 * должно быть брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные.
 * 3. В методе main() вызвать полученный метод,
 * обработать возможные исключения MyArraySizeException и MyArrayDataException и вывести результат расчета.
 */
public class MyArrayClass {

    /**
     * Вариант метода с проверкми вначале на размерность
     */
    public static void acceptArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length != 4) throw new MyArraySizeException("Размер массива не равен 4х4");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != 4) throw new MyArraySizeException("Размер массива не равен 4х4");
        }

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException | NullPointerException e) {
                    throw new MyArrayDataException("Ячейка [" + i + ";" + j + "] сожержит не Integer, a (" +
                        arr[i][j] + ")");
                }
            }
        }
        System.out.println("Метод выполнен успешно.\n" +
            "Сумма массива arr = " + Arrays.deepToString(arr) +
            "\nsum = " + sum);
    }

    /**
     * Вариант метода с проверками на размерность по ходу суммирования
     * (не учитывает, что размер может быть больше 4х4 !!!)
     */
    public static void acceptArrayVersion2(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException | NullPointerException e) {
                    throw new MyArrayDataException("Ячейка [" + i + ";" + j + "] сожержит не Integer, a (" +
                        arr[i][j] + ")");
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new MyArraySizeException("Размер массива не равен 4х4");
                }
            }
        }
        System.out.println("Метод v2 выполнен успешно.\n" +
            "Сумма массива arr = " + Arrays.deepToString(arr) +
            "\nsum = " + sum);
    }

    public static void main(String[] args) {
        System.out.println("Вызов метода с массивом 4х3");
        try {
            acceptArray(new String[4][3]);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println("Вызов метода с массивом 4х4. Но кривыми данными в ячейке 1;2");
        String[][] arr = new String[][]{{"1", "2", "3", "4"}, {"1", "2", "NotInt", "4"}, {"1", "2", "3", "4"},
            {"1", "2", "3", "4"}};
        try {
            acceptArray(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println("Вызов метода с массивом 4х4 с корректными данными");
        arr = new String[][]{{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        try {
            acceptArray(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }

        System.out.println("Вызов метода v2 с массивом 4х3");
        arr = new String[][]{{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        try {
            acceptArrayVersion2(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println("Вызов метода v2 с массивом 4х4. Но кривыми данными в ячейке 1;2");
        arr = new String[][]{{"1", "2", "3", "4"}, {"1", "2", "NotInt", "4"}, {"1", "2", "3", "4"},
            {"1", "2", "3", "4"}};
        try {
            acceptArrayVersion2(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println("Вызов метода v2 с массивом 4х4 с корректными данными");
        arr = new String[][]{{"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}, {"1", "2", "3", "4"}};
        try {
            acceptArrayVersion2(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}
