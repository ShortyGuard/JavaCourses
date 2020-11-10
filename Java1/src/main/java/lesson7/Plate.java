package lesson7;

public class Plate {
    private int food;

    public Plate(int food) {
        if (food < 0) {
            throw new IllegalArgumentException("Food must be positive or zero");
        }
        this.food = food;
    }

    public boolean decreaseFood(int count) {
        if (count > food || count < 0) return false;

        food -= count;

        return true;
    }

    public void addFood(int count) {
        if (count <= 0) throw new IllegalArgumentException("Count of food must be positive");

        this.food += count;
    }

    public void info() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Plate{" +
            "food=" + food +
            '}';
    }
}