package lesson6;

public class Dog extends Animal {
    private static long countOfCreatedInstances = 0;

    public Dog(String name, String color, int age) {
        super(name, color, age,
            (float) Math.random() * 4990 + 10, //пусть союаки бегают от 10 до 5000 метров
            (float) Math.random() * 100, //собаки плавают от 0 до 100 метров
            (float) Math.random() * 4 + 1//собаки могут прыгать от 1 до 5 метров
        );

        countOfCreatedInstances++;
    }

    public static long countOfCreatedInstances() {
        return countOfCreatedInstances;
    }
}
