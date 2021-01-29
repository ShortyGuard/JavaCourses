package lesson7;


public class Test1 {

    @MyBeforeSuite
    public void before() {
        System.out.println("before");
    }

    @MyAfterSuite
    public void after() {
        System.out.println("after");
    }

    @MyTest
    @MyPriority(weght = 10)
    public void test1() {
        System.out.println("Test 1");
    }

    @MyTest
    @MyPriority(weght = 4)
    public void test2() {
        System.out.println("Test 2");
    }

    @MyTest
    public void test3() {
        System.out.println("Test 3");
    }

    @MyTest
    @MyPriority(weght = 100)
    public void test4() {
        System.out.println("Test 4");
    }

    @MyTest
    @MyPriority(weght = 4)
    public void test5() {
        System.out.println("Test 5");
    }

}
