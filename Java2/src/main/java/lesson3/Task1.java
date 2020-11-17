package lesson3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Задание 1:
 * Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать, сколько раз встречается каждое слово.
 */
public class Task1 {
    public static void main(String[] args) {
        //Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся)
        String[] words = {"яблоко", "груша", "вишня", "черешня", "арбуз", "ранетка", "клубника", "яблоко", "черешня",
            "мандарин", "груша", "яблоко", "арбуз", "яблоко", "мандарин", "груша", "черешня", "арбуз",
            "апельсин", "вишня", "яблоко", "арбуз", "груша", "папайя", "банан", "крыжовник"};
        System.out.println("Исходный массив: " + Arrays.toString(words));

        //Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
        HashSet<String> stringHashSet = new HashSet<>(Arrays.asList(words));
        System.out.println("Уникальные значения исходного массива: " + stringHashSet.toString());

        //Посчитать, сколько раз встречается каждое слово.
        HashMap<String, Integer> wordCountsMap = new HashMap<>();
        for (String word : words) {
            wordCountsMap.put(word, wordCountsMap.getOrDefault(word, 0) + 1);
        }
        System.out.println("Слова с количествами повторний в исходном массиве: " + wordCountsMap.toString());
    }
}
