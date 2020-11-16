package lesson1;

public class Track implements Obstacle {
    private int distance;

    public Track() {
        this.distance  = (int) (Math.random() * 2000);
    }

    @Override
    public int getDistance() {
        return this.distance;
    }

    @Override
    public String toString() {
        return "Track{" +
            "distance=" + distance +
            '}';
    }

    @Override
    public boolean haveToRun() {
        return true;
    }

    @Override
    public boolean haveToJump() {
        return false;
    }
}
