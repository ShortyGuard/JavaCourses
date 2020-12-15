package lesson1;

import java.util.ArrayList;

/*
Ограничиваем добавление разных фруктов указав наследование T сразу от двух интерфейсов Fruit & FruitProperties
 */
public class Box<T extends Fruit & FruitProperties> {
    private final ArrayList<T> fruits = new ArrayList<>();

    public void add(T fruit){
        this.fruits.add(fruit);
    }

    public float getWeight(){
        float   totalWeight  = 0;
        for (T fruit: fruits) {
            totalWeight += fruit.getWeight();
        }

        return totalWeight;
    }

    public boolean compare(Box box){
        return this.getWeight() == box.getWeight();
    }

    public void pourItOverToBox(Box box){
        for (T fruit: fruits) {
            box.add(fruit);
        }
        fruits.clear();
    }
}
