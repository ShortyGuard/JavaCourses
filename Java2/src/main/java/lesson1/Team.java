package lesson1;

import java.util.Arrays;

public class Team {

    private int countOfMemebers;
    private TeamMember[] members;

    public Team(int countOfMemebers) {
        this.countOfMemebers = countOfMemebers;
        this.generateTeam();
    }

    private void generateTeam() {
        this.members = new TeamMember[this.countOfMemebers];
        for (int i = 0; i < this.members.length; i++) {
            double rand = Math.random();
            if (rand < 0.33) {
                this.members[i] = new Human();
            } else if (rand < 0.66) {
                this.members[i] = new Robot();
            } else {
                this.members[i] = new Cat();
            }
        }
    }

    public void showResults() {
        for (TeamMember teamMember : this.members) {
            teamMember.showResult();
        }
    }

    public TeamMember[] getMembers() {
        return this.members;
    }

    @Override
    public String toString() {
        return "Team{" +
            "members=" + Arrays.toString(members) +
            '}';
    }
}
