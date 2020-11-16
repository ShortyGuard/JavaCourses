package lesson1;

import lombok.Getter;

public class PassResult {
    @Getter
    private boolean hasPassed;
    @Getter
    private Obstacle obstacle;

    public PassResult(boolean hasPassed, Obstacle obstacle) {
        this.hasPassed = hasPassed;
        this.obstacle = obstacle;
    }
}
