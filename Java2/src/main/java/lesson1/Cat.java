package lesson1;

import java.util.ArrayList;
import java.util.List;

public class Cat implements TeamMember {
    private List<PassResult> results = new ArrayList<PassResult>();
    int runDistance;
    int jumpDestance;

    public Cat() {
        this.runDistance = (int) (Math.random() * 1500);
        this.jumpDestance = (int) (Math.random() * 3);
    }

    @Override
    public void run(Obstacle obstacle) {
        if (!obstacle.haveToRun()) return;
        System.out.println("Run");
        this.results.add(new PassResult(obstacle.getDistance() <= this.runDistance, obstacle));
    }


    @Override
    public void jump(Obstacle obstacle) {
        if (!obstacle.haveToJump()) return;
        System.out.println("Jump");
        this.results.add(new PassResult(obstacle.getDistance() <= this.jumpDestance, obstacle));
    }

    @Override
    public MemberType getMembertype() {
        return MemberType.CAT;
    }

    @Override
    public void passObstacles(Obstacle[] obstacles) {
        for (Obstacle obstacle : obstacles) {
            PassResult lastResult = this.results.size() > 0 ? this.results.get(this.results.size() - 1) : null;
            if (lastResult != null && !lastResult.isHasPassed()) break;
            this.run(obstacle);
            this.jump(obstacle);
        }
    }

    @Override
    public void showResult() {
        System.out.println("Результаты участника " + this);
        for (PassResult passResult: results){
            System.out.println(passResult.getObstacle() + ": " + passResult.isHasPassed());
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
            "runDistance=" + runDistance +
            ", jumpDestance=" + jumpDestance +
            '}';
    }
}
