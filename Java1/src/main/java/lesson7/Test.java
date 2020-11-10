package lesson7;

import java.util.Arrays;

/**
 * 1. Расширить задачу про котов и тарелки с едой
 * 2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды
 * (например, в миске 10 еды, а кот пытается покушать 15-20)
 * 3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
 * Если коту удалось покушать (хватило еды), сытость = true
 * 4. Считаем, что если коту мало еды в тарелке, то он ее просто не трогает,
 * то есть не может быть наполовину сыт (это сделано для упрощения логики программы)
 * 5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки
 * и потом вывести информацию о сытости котов в консоль
 * 6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку
 */
public class Test {

    public static void main(String[] args) {
        Cat[] cats  = new Cat[]{new Cat("Barsik", 5),
            new Cat("Pushok", 4),
            new Cat("Vasiliy", 7)};

        Plate plate = new Plate(15);

        plate.info();
        System.out.println(Arrays.toString(cats));

        cats[0].eat(plate);
        cats[1].eat(plate);
        cats[2].eat(plate);

        plate.info();
        System.out.println(Arrays.toString(cats));
    }
}