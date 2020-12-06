package lesson1;

public class PassResult {

    private boolean hasPassed;
    private Obstacle obstacle;

    public boolean isHasPassed() {
        return hasPassed;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public PassResult(boolean hasPassed, Obstacle obstacle) {
        this.hasPassed = hasPassed;
        this.obstacle = obstacle;
    }
}
