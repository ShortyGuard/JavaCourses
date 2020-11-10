package lesson7;

public class Cat {
    private String name;
    private int appetite;
    private boolean fullness;

    public Cat(String name, int appetite) {
        if (appetite <= 0) {
            throw new IllegalArgumentException("Appetite must be positive");
        }
        this.name = name;
        this.appetite = appetite;
        this.fullness = false;
    }

    public void eat(Plate p) {
        if (p.decreaseFood(appetite)) {
           this.fullness    = true;
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
            "name='" + name + '\'' +
            ", appetite=" + appetite +
            ", fullness=" + fullness +
            '}';
    }
}