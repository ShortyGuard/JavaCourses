package lesson6;

public class AnimalTest {

    public static void main(String[] args) {
        Animal cat = new Cat("Барсик", "рыжий", 3);
        System.out.println(cat);
        Animal dog = new Dog("Рекс", "черный", 5);
        System.out.println(dog);
        Animal cat2 = new Cat("Пират", "черный", 3);
        System.out.println(cat2);
        Animal dog2 = new Dog("Лесси", "колли", 2);
        System.out.println(dog2);

        cat.run(50);
        cat2.run(50);
        cat.swim(3);
        cat2.swim(3);
        cat.jumpOver(3);
        cat2.jumpOver(3);

        dog.run(50);
        dog2.run(50);
        dog.swim(3);
        dog2.swim(3);
        dog.jumpOver(3);
        dog2.jumpOver(3);

        System.out.println("Создано котов: " + Cat.countOfCreatedInstances());
        System.out.println("Создано собак: " + Dog.countOfCreatedInstances());

    }
}