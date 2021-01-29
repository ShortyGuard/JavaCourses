package lesson6;

/**
 * Написать метод, который проверяет состав массива из чисел 1 и 4.
 * Если в нем нет хоть одной четверки или единицы, то метод вернет false;
 * Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */
public class Task3 {

    public static boolean checkIfOneOrFourContains(int[] arr) {
        boolean hasOne = false;
        boolean hasFour = false;
        for (int i : arr) {
            if (i == 1) {
                hasOne = true;
            }
            if (i == 4) {
                hasFour = true;
            }
        }
        return hasOne && hasFour;
    }
}
