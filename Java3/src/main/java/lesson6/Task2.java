package lesson6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
 * Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
 * идущих после последней четверки.
 * Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
 * Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
 */
public class Task2 {

    public static int[] getArrayAfterLastFour(int[] arr) {

        int lastFourIndex = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                lastFourIndex = i;
            }
        }

        if (lastFourIndex == -1) {
            throw new RuntimeException("Array has not any fours");
        }

        int[] result = new int[arr.length - lastFourIndex - 1];
        System.arraycopy(arr, lastFourIndex + 1, result, 0, arr.length - lastFourIndex - 1);
        return result;
    }
}
