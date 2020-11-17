package lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Задание 2:
 * Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи,
 * а с помощью метода get() искать номер телефона по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 * Желательно не добавлять лишний функционал (дополнительные поля (имя, отчество, адрес),
 * взаимодействие с пользователем через консоль и т.д).
 * Консоль использовать только для вывода результатов проверки телефонного справочника.
 */
public class PhoneContactsVersion1 {

   private final Map<String, Set<String>> contacts = new HashMap<>();

    public void add(String name, String phoneNumber) {
        Set<String> phonesByName = this.get(name);
        phonesByName.add(phoneNumber);
        this.contacts.put(name, phonesByName);
    }

    /**
     * В этом варианте, если контакта нет, то вернется пустая коллекция
     */
    public Set<String> get(String name) {
        Set<String> result = this.contacts.get(name);
        if (result == null) {
            result = new HashSet<>();
        }

        return result;
    }

    @Override
    public String toString() {
        return "PhoneContacts{" +
            "contacts=" + contacts +
            '}';
    }
}
