package lesson1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BoxVersion2Test {

    @Test
    public void getWeigthTest(){
        BoxVersion2<Fruit> fruitBoxApples = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        assertEquals(10, fruitBoxApples.getWeight(), 0);


        BoxVersion2<Fruit> fruitBoxOranges = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxOranges.add(new Orange());
        }

        assertEquals(15, fruitBoxOranges.getWeight(), 0);
    }

    @Test
    public void compareTest(){
        BoxVersion2<Fruit> fruitBoxApples = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        BoxVersion2<Fruit> fruitBoxOranges = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxOranges.add(new Orange());
        }

        assertFalse(fruitBoxApples.compare(fruitBoxOranges));
        assertFalse(fruitBoxOranges.compare(fruitBoxApples));

        for (int i = 0; i < 5; i++) {
            fruitBoxApples.add(new Apple());
        }

        assertTrue(fruitBoxApples.compare(fruitBoxOranges));
        assertTrue(fruitBoxOranges.compare(fruitBoxApples));
    }

    @Test
    public void pourItOverToBoxTest(){
        BoxVersion2<Fruit> fruitBoxApples = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        BoxVersion2<Fruit> newFruitBoxApples = new BoxVersion2<>();
        fruitBoxApples.pourItOverToBox(newFruitBoxApples);

        assertEquals(0, fruitBoxApples.getWeight(), 0);
        assertEquals(10, newFruitBoxApples.getWeight(), 0);
    }

    @Test
    public void addOtherFruitTypeTest(){
        BoxVersion2<Fruit> fruitBoxApples = new BoxVersion2<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        try {
            fruitBoxApples.add(new Orange());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Нельзя положить в корзину разные типы фруктов");
        }
    }
}
