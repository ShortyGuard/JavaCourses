package lesson1;

import java.util.ArrayList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Task1And2Test {

    @Test
    public void swapElementsTest() {
        String[] strings = {"qwert", "asdf", "zxcv", "1qaz"};
        String[] swapedStrings = Task1And2.swapElements(strings, 0, 3);
        assertEquals("qwert", swapedStrings[3]);
        assertEquals("1qaz", swapedStrings[0]);

        Double[] doubles = {1.2, 3.4, 7.2, 3.5, 1.5};
        Double[] swapedDoubles = Task1And2.swapElements(doubles, 2, 3);
        assertEquals(Double.valueOf(7.2), swapedDoubles[3]);
        assertEquals(Double.valueOf(3.5), swapedDoubles[2]);
    }

    @Test
    public void swapElementsCheckArgumetsTest() {
        String[] strings = {"qwert", "asdf", "zxcv", "1qaz"};
        try {
            Task1And2.swapElements(strings, -1, 2);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Переданы некорректные параметры");
        }
        try {
            Task1And2.swapElements(strings, 0, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Переданы некорректные параметры");
        }
        try {
            Task1And2.swapElements(null, 0, 2);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Переданы некорректные параметры");
        }
    }

    @Test
    public void mapToArrayListTest() {
        String[] strings = {"qwert", "asdf", "zxcv", "1qaz"};
        ArrayList<String> stringArrayList = Task1And2.mapToArrayList(strings);

        for (int i = 0; i < strings.length; i++) {
            assertEquals(strings[i], stringArrayList.get(i));
        }

        Double[] doubles = {1.2, 3.4, 7.2, 3.5, 1.5};
        ArrayList<Double> doubleArrayList = Task1And2.mapToArrayList(doubles);
        for (int i = 0; i < doubles.length; i++) {
            assertEquals(doubles[i], doubleArrayList.get(i));
        }
    }


    @Test
    public void mapToArrayListCheckArgumetsTest() {
        String[] strings = null;
        try {
            Task1And2.mapToArrayList(strings);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Переданный массив пустой");
        }

        Double[] doubles = {};
        try {
            Task1And2.mapToArrayList(doubles);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Переданный массив пустой");
        }
    }

}
