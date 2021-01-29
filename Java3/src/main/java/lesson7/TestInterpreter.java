package lesson7;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import lombok.SneakyThrows;

/**
 * Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов
 * с аннотациями @Test. Для этого у него должен быть статический метод start(), которому в качестве параметра
 * передается или объект типа Class, или имя класса.
 * Из «класса-теста» вначале должен быть запущен
 * метод с аннотацией @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test,
 * а по завершению всех тестов – метод с аннотацией @AfterSuite.
 * <p>
 * К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми
 * будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет значения.
 * Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре,
 * иначе необходимо бросить RuntimeException при запуске «тестирования».
 */
public class TestInterpreter {

    @SneakyThrows
    public static void start(Class<?> clazz) {

        // возьмем все методы класса
        Method[] declaredMethods = clazz.getDeclaredMethods();
        // пробежим по методам и отделим before, after и tests
        Method before = null;
        Method after = null;
        SortedMap<Integer, Set<Method>> tests = new TreeMap<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(MyBeforeSuite.class)) {
                if (before != null) {
                    throw new IllegalArgumentException(clazz.getName() + " has more than one @MyBeforeSuite annotation");
                } else {
                    before = declaredMethod;
                }
            }
            if (declaredMethod.isAnnotationPresent(MyAfterSuite.class)) {
                if (after != null) {
                    throw new IllegalArgumentException(clazz.getName() + " has more than one @MyAfterSuite annotation");
                } else {
                    after = declaredMethod;
                }
            }
            if (declaredMethod.isAnnotationPresent(MyTest.class)) {
                int testPriority = declaredMethod.getAnnotation(MyPriority.class) != null ?
                    declaredMethod.getAnnotation(MyPriority.class).weght() : 0;

                Set<Method> methods = tests.computeIfAbsent(testPriority, k -> new HashSet<>());
                methods.add(declaredMethod);
            }
        }
        System.out.println(tests);

        // создадим экземпляр класса
        Class<?>[] params = {};
        Object o = clazz.getConstructor(params).newInstance();

        // выполняем @MyBeforeSuite
        if (before != null) {
            before.invoke(o);
        }

        // выполняем тесты по приоритетам
        for (Set<Method> methods : tests.values()) {
            for (Method method : methods) {
                method.invoke(o);
            }
        }

        // выполняем @MyAfterSuite
        if (after != null) {
            after.invoke(o);
        }

    }
}
