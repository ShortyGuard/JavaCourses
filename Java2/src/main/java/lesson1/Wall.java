package lesson1;

public class Wall implements Obstacle {
    private int distance;

    public Wall() {
        this.distance  = (int) (Math.random() * 2);
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return "Wall{" +
            "distance=" + distance +
            '}';
    }

    @Override
    public boolean haveToRun() {
        return false;
    }

    @Override
    public boolean haveToJump() {
        return true;
    }
}
