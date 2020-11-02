package lesson6;

public abstract class Animal {
    protected String name;
    protected String color;
    protected int age;
    protected float maxRunDistance;
    protected float maxSwimDistance;
    protected float maxObstacleHeight;

    public Animal(String name, String color, int age, float maxRunDistance, float maxSwimDistance, float maxObstacleHeight) {

        this.name = name;
        this.color = color;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.maxSwimDistance = maxSwimDistance;
        this.maxObstacleHeight = maxObstacleHeight;
    }

    public void run(float distance) {
        if (maxRunDistance == 0) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не умеет бегать");
        } else if (distance <= 0 || distance > maxRunDistance) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не может пробежать дистанцию " + distance);
        } else {
            System.out.println(this.getClass().getSimpleName() + " " + name + " пробежал дистанцию " + distance);
        }
    }

    public void swim(float distance) {
        if (maxSwimDistance == 0) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не умеет плавать");
        } else if (distance <= 0 || distance > maxSwimDistance) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не может проплыть дистанцию " + distance);
        } else {
            System.out.println(this.getClass().getSimpleName() + " " + name + " проплыл дистанцию " + distance);
        }
    }

    public void jumpOver(float obstacleHeight) {
        if (maxObstacleHeight == 0) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не умеет перепрыгивать препятствия");
        } else if (obstacleHeight <= 0 || obstacleHeight > maxObstacleHeight) {
            System.out.println(this.getClass().getSimpleName() + " " + name + " не может перепрыгнуть препятствие высотой "
                + obstacleHeight);
        } else {
            System.out.println(this.getClass().getSimpleName() + " " + name + " перепрыгнул препятствие высотой " + obstacleHeight);
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
            "animalType='" + this.getClass().getSimpleName() + '\'' +
            ", name='" + name + '\'' +
            ", color='" + color + '\'' +
            ", age=" + age +
            ", maxRunDistance=" + maxRunDistance +
            ", maxSwimDistance=" + maxSwimDistance +
            ", maxObstacleHeight=" + maxObstacleHeight +
            '}';
    }

    public static long countOfCreatedInstances() {
        return 0;
    }
}
