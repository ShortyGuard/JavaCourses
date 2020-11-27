package lesson5;

import java.util.Arrays;

public class HomeTaskLesson5 {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public void firstMethod() {
        System.out.println("Старт метода 1.");

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Время работы метода 1: " + (endTime - startTime) + "мсек.");

    }

    public void secondMethod() throws InterruptedException {
        System.out.println("Старт метода 2.");

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);


        long startTime = System.currentTimeMillis();

        //разбиваем массив на 2 части
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];

        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        // описываем 2 реализации работы с каждой половиной
        Runnable firstHalf = () -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        };
        Runnable secondHalf = () -> {
            for (int i = 0; i < arr2.length; i++) {
                int index = i + HALF;
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + index / 5) * Math.cos(0.2f + index / 5)
                    * Math.cos(0.4f + index / 2));
            }
        };

        //создаем два потока
        Thread threadForFirstHalf = new Thread(firstHalf);
        Thread threadForSecondHalf = new Thread(secondHalf);
        //стартуем потоки
        threadForFirstHalf.start();
        threadForSecondHalf.start();
        //ждем когда потоки выполнятся
        threadForFirstHalf.join();
        threadForSecondHalf.join();

        //собираем массив из половинок
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

        long endTime = System.currentTimeMillis();

        System.out.println("Время работы метода 2: " + (endTime - startTime) + "мсек.");
    }

    public static void main(String[] args) throws InterruptedException {

        HomeTaskLesson5 homeTaskLesson5 = new HomeTaskLesson5();

        homeTaskLesson5.firstMethod();

        homeTaskLesson5.secondMethod();

    }
}
