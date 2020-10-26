package lesson5;

/**
 * Домашнее задание к уроку 5:
 * * Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст;
 * * Конструктор класса должен заполнять эти поля при создании объекта;
 * * Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль;
 * * Создать массив из 5 сотрудников
 * Пример:
 * Person[] persArray = new Person[5]; // Вначале объявляем массив объектов
 * persArray[0] = new Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30); // потом для каждой ячейки массива задаем объект
 * persArray[1] = new Person(...);
 * ...
 * persArray[4] = new Person(...);
 * <p>
 * * С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
 */
public class Employer {
    //ФИО
    private String fio;
    //должность
    private String position;
    //email
    private String email;
    //телефон
    private String phoneNumber;
    //зарплата
    private int salary;
    //возраст
    private int age;

    public Employer(String fio, String position, String email, String phoneNumber, int salary, int age) {
        this.fio = fio;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.age = age;
    }

    public String getFio() {
        return fio;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        return age;
    }

    /**
     * Этот метод - это выполнение "Внутри класса «Сотрудник» написать метод,
     * который выводит информацию об объекте в консоль;"
     * <p>
     * Хотя toString() кмк вполне достаточно
     */
    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Employer{" +
            "fio='" + fio + '\'' +
            ", position='" + position + '\'' +
            ", email='" + email + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", salary=" + salary +
            ", age=" + age +
            '}';
    }

    public static void main(String[] args) {
        Employer[] employers = new Employer[5];
        employers[0] = new Employer("Иванов Иван Иванович", "бухгалтер", "ivan@ivana.net",
            "8-911-111-11-11", 30_000, 33);
        employers[1] = new Employer("Петров Петр Петрович", "клерк", "petr@petra.net",
            "8-922-222-22-22", 20_000, 38);
        employers[2] = new Employer("Сергеев Сергей Сергеевич", "директор", "serhey@sergeya.net",
            "8-933-333-33-33", 300_000, 43);
        employers[3] = new Employer("Александров Александр Александрович", "разработчик", "sashф@sashi.net",
            "8-944-444-44-44", 230_000, 43);
        employers[4] = new Employer("Михеев Михаил Михайлович", "тестировщик", "misha@mishi.net",
            "8-955-555-55-55", 130_000, 23);

        System.out.println("Все сотрудники старше 40:");
        for (Employer employer : employers) {
            if (employer.getAge() > 40) {
                System.out.println(employer);
            }
        }
    }
}
