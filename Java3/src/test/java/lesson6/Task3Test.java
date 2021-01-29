package lesson6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Task3Test {


    private static Stream<Arguments> getTrueData() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 1, 1, 4, 4, 1, 4, 4}));
        out.add(Arguments.arguments(new int[]{1, 4}));
        out.add(Arguments.arguments(new int[]{4, 1}));
        return out.stream();
    }

    private static Stream<Arguments> getFalseData() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{1, 1, 1, 1, 1, 1}));
        out.add(Arguments.arguments(new int[]{4, 4, 4, 4}));
        out.add(Arguments.arguments(new int[]{2, -4, 0, 1, 1, 5, 3}));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("getTrueData")
    public void massTrueTest(int[] array) {
        assertTrue(Task3.checkIfOneOrFourContains(array));
    }

    @ParameterizedTest
    @MethodSource("getFalseData")
    public void massFalseTest(int[] array) {
        assertFalse(Task3.checkIfOneOrFourContains(array));
    }
}