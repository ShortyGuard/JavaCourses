package lesson1;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BoxTest {

    @Test
    public void getWeigthTest(){
        Box<Apple> fruitBoxApples = new Box<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        assertEquals(10, fruitBoxApples.getWeight(), 0);


        Box<Orange> fruitBoxOranges = new Box<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxOranges.add(new Orange());
        }

        assertEquals(15, fruitBoxOranges.getWeight(), 0);
    }

    @Test
    public void compareTest(){
        Box<Apple> fruitBoxApples = new Box<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        Box<Orange> fruitBoxOranges = new Box<>();

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
        Box<Apple> fruitBoxApples = new Box<>();

        for (int i = 0; i < 10; i++) {
            fruitBoxApples.add(new Apple());
        }

        Box<Apple> newFruitBoxApples = new Box<>();
        fruitBoxApples.pourItOverToBox(newFruitBoxApples);

        assertEquals(0, fruitBoxApples.getWeight(), 0);
        assertEquals(10, newFruitBoxApples.getWeight(), 0);
    }
}
