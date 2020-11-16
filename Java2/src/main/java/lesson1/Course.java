package lesson1;

import lombok.Getter;

import java.util.Arrays;

public class Course {
    private int countOfObtstacles;
    @Getter
    private Obstacle[] obstacles;

    public Course(int countOfObtstacles) {

        this.countOfObtstacles = countOfObtstacles;

        this.generateCourse();
    }

    private void generateCourse() {
        this.obstacles  = new Obstacle[this.countOfObtstacles];
        for (int i = 0; i < this.countOfObtstacles; i++) {
            if (Math.random() < 0.5)
            {
                this.obstacles[i]   = new Wall();
            } else {
                this.obstacles[i]   = new Track();
            }
        }
    }

    public void passObstacleCourse(Team team) {
        for (TeamMember teamMember : team.getMembers()) {
            teamMember.passObstacles(this.obstacles);
        }
    }

    @Override
    public String toString() {
        return "Course{" +
            "obstacles=" + Arrays.toString(obstacles) +
            '}';
    }
}
