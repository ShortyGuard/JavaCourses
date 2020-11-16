package lesson1;

public interface TeamMember {
    void jump(Obstacle obstacle);
    void run(Obstacle obstacle);
    MemberType getMembertype();

    void passObstacles(Obstacle[] obstacles);

    void showResult();
}
