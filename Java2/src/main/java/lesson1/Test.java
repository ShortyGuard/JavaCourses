package lesson1;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        Course c = new Course(10); // Создаем полосу препятствий
        System.out.println("Полоса препятсвий: " + c);
        Team team = new Team(5); // Создаем команду
        System.out.println("Команда: " + team);
        System.out.println("Старт!!!");
        c.passObstacleCourse(team); // Просим команду пройти полосу
        team.showResults(); // Показываем результаты
    }
}
