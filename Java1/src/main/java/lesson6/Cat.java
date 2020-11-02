package lesson6;

import lombok.Builder;

public class Cat extends Animal {
    private static long countOfCreatedInstances = 0;

    public Cat(String name, String color, int age) {
        super(name, color, age,
            (float) Math.random() * 290 + 1, //пусть коты бегают от 1 до 300 метров
            0, //коты не плавают совсем
            (float) Math.random() * 2 + 1//Коты могут прыгать от 1 до 3 метров
        );

        countOfCreatedInstances++;
    }

    public static long countOfCreatedInstances() {
        return countOfCreatedInstances;
    }
}

