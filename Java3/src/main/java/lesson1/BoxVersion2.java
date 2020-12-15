package lesson1;

import java.util.ArrayList;

public class BoxVersion2<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        if (!fruits.isEmpty()){
            T fruitInList    = fruits.get(0);
            if (!fruitInList.getClass().getName().equals(fruit.getClass().getName())){
                throw new IllegalArgumentException("Нельзя положить в корзину разные типы фруктов");
            }
        }
        this.fruits.add(fruit);
    }

    public float getWeight() {
        float totalWeight = 0;
        for (T fruit : fruits) {
            totalWeight += fruit.getWeight();
        }

        return totalWeight;
    }

    public boolean compare(BoxVersion2 box) {
        return this.getWeight() == box.getWeight();
    }

    public void pourItOverToBox(BoxVersion2 box) {
        for (T fruit : fruits) {
            box.add(fruit);
        }
        fruits.clear();
    }
}
