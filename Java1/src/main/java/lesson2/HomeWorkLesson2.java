package lesson2;

import java.util.Arrays;

/**
 * Домашнее задание курса Java. Уровень 1. Урок 2
 */
public class HomeWorkLesson2 {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();

        //Вызов задания 6
        //checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true
        checkBalance(new int[]{2, 2, 2, 1, 2, 2, 10, 1});
        //checkBalance([1, 1, 1, || 2, 1]) → true
        checkBalance(new int[]{1, 1, 1, 2, 1});
        //checkBalance([1, 1, 1, 2, 1, || 20]) → false
        checkBalance(new int[]{1, 1, 1, 2, 1, 20});

        //вызовы задания 7
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, 1);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, 2);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, -1);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, -2);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, 12);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, 13);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, -12);
        task7(new int[]{1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1}, -13);
    }

    /**
     * 1.  Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
     * С помощью цикла и условия заменить 0 на 1, 1 на 0.
     */
    public static void task1() {
        System.out.println("Задание 1.");
        int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        System.out.println("Инициализированный массив: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = 1;
            } else {
                arr[i] = 0;
            }
        }
        System.out.println("Преобразованный массив: " + Arrays.toString(arr));
    }

    /**
     * 2.  Задать пустой целочисленный массив размером 8. С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21.
     */
    public static void task2() {
        System.out.println("Задание 2.");
        int[] arr = new int[8];
        System.out.println("Инициализированный массив: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i * 3;
        }
        System.out.println("Заполненный массив: " + Arrays.toString(arr));
    }

    /**
     * 3.  Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ], пройти по нему циклом и числа меньшие 6 умножить на 2
     */
    public static void task3() {
        System.out.println("Задание 3.");
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Инициализированный массив: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        System.out.println("Преобразованный массив: " + Arrays.toString(arr));
    }

    /**
     * 4.  Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое)
     * и с помощью цикла (-ов) заполнить его диагональные элементы единицами.
     */
    public static void task4() {
        System.out.println("Задание 4.");
        int[][] arr = new int[5][5];
        System.out.println("Инициализированный массив: " + Arrays.deepToString(arr));
        for (int i = 0; i < arr.length; i++) {
            arr[i][i] = 1;
            arr[arr.length - i - 1][i] = 1;
        }
        System.out.println("Преобразованный массив: " + Arrays.deepToString(arr));
    }

    /**
     * 5. ** Задать одномерный массив и найти в нем минимальный и максимальный элементы (без помощи интернета);
     */
    public static void task5() {
        System.out.println("Задание 5.");
        int[] arr = {6, 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Инициализированный массив: " + Arrays.toString(arr));
        int max = arr[0];
        int min = arr[0];
        for (int i : arr) {
            if (max < i) {
                max = i;
            } else if (min > i) {
                min = i;
            }
        }
        System.out.println("max : " + max + ", min: " + min);
    }

    /**
     * 6. ** Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть true,
     * если в массиве есть место, в котором сумма левой и правой части массива равны.
     * Примеры:
     * checkBalance([2, 2, 2, 1, 2, 2, || 10, 1]) → true, checkBalance([1, 1, 1, || 2, 1]) → true,
     * граница показана символами ||, эти символы в массив не входят.
     */
    public static boolean checkBalance(int[] arr) {
        System.out.println("Задание 6.");
        System.out.println("Переданный массив: " + Arrays.toString(arr));
        int leftSum = 0;
        int rightSum = 0;
        //будем двигаться по массиву сразу с двух сторон, пока не береберем все элементы массива
        for (int i = 0, j = arr.length - 1; i <= j; ) {
            //если левая сумма меньше либо равна правой, то прибавим i-й элемент к левой части и сдвинем i на 1
            if (leftSum <= rightSum) {
                leftSum += arr[i];
                i++;
            } else {
                //если правая часть меньше левой, то то прибавим j-й элемент к правой части и сдвинем j на -1
                rightSum += arr[j];
                j--;
            }
        }

        //если полученные суммы неравны и если массив меньше 2ух элементов, то вернем false
        boolean result = leftSum == rightSum && arr.length > 1;
        System.out.println("Результат работы функции: " + result);
        return result;
    }

    /**
     * 7. **** Написать метод, которому на вход подается одномерный массив и число n (может быть положительным,
     * или отрицательным), при этом метод должен сместить все элементымассива на n позиций.
     * Для усложнения задачи нельзя пользоваться вспомогательными массивами.
     */
    public static int[] task7(int[] arr, int n) {
        System.out.println("Задание 7.");
        System.out.println("Переданный массив: " + Arrays.toString(arr));
        System.out.println("Переданное смещение: " + n);
        //если смещение кратно размеру массива или массив из менее чем 2ух элементов, то ничего не делаем
        if (arr == null || arr.length < 2 || n % arr.length == 0) {
            System.out.println("преобразование не требуется");
            return arr;
        }

        //смешение на кратное длине массива число даст изначальный массив, поэтому исключим лишние итерации
        n %= arr.length;

        //чтобы не писать 2 разных по направлению цикла перебора массива примем, что направление сдвига будет
        //всегда отрицательным (влево). Для этого если задан положительный вектор приведем его к отрицательному
        //оптимизировать число сдвигов при этом не будем (оставим на факультатив)
        if (n > 0) {
            n = n - arr.length;
        }

        //будем делать n сдвигов влево по 1 элементу (2умя циклами)
        for (int i = 0; i > n; i--) {
            //сдвинем на 1 элемент влево
            //запомним первый элемент
            int firstElement = arr[0];
            //сддвинем все элементы на 1 влево начиная со второго элемента массива
            for (int j = 1; j < arr.length; j++) {
                arr[j - 1] = arr[j];
            }
            //последнему элементу присвоим запомненный первый
            arr[arr.length - 1] = firstElement;
        }
        System.out.println("Преобразованный массив: " + Arrays.toString(arr));
        return arr;
    }
}
