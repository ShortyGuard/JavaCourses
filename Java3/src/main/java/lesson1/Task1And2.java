package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Task1And2 {

    /**
     * 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
     * <p>
     * !!! т.к. элементы меняются в переданном массиве, то возвращать его не обязательно (можно и void сделать)
     */
    public static <T> T[] swapElements(T[] arr, int index1, int index2) {
        if (index1 < 0 || index2 < 0 || arr == null || index1 > arr.length - 1 || index2 > arr.length - 1) {
            throw new IllegalArgumentException("Переданы некорректные параметры");
        }
        T firstElement = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = firstElement;

        return arr;
    }

    /**
     * 2. Написать метод, который преобразует массив в ArrayList;
     */
    public static <E> ArrayList<E> mapToArrayList(E[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Переданный массив пустой");
        }
        ArrayList<E> arrayList = new ArrayList<>(arr.length);

        arrayList.addAll(Arrays.asList(arr));
        return (arrayList);
    }

}
