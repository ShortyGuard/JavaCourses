package lesson6;

import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class Task2Test {

    @Test
    public void getArrayAfterLastFour() {
        int[] arr = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};

        int[] result = Task2.getArrayAfterLastFour(arr);

        assertEquals(2, result.length);
        assertArrayEquals(new int[]{1, 7}, result);

        arr = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7, 4};
        result = Task2.getArrayAfterLastFour(arr);

        assertEquals(0, result.length);

        arr = new int[]{4, -2, 4, -4, 2, 3, 44, 1, 7};
        result = Task2.getArrayAfterLastFour(arr);

        assertEquals(6, result.length);
        assertArrayEquals(new int[]{-4, 2, 3, 44, 1, 7}, result);

    }

    @Test(expected = RuntimeException.class)
    public void getArrayAfterLastFourValidation() {
        int[] arr = new int[]{1, 2, 0, 2, 2, 3, 12, 1, 7};

        Task2.getArrayAfterLastFour(arr);
    }
}