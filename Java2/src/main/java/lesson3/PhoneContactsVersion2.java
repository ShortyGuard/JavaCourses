package lesson3;

import java.util.HashMap;
import java.util.HashSet;
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
 *
 * Вариант справочника как расширение HashMap
 * В этом варианте, если контакта нет, то вернется null
 */
public class PhoneContactsVersion2 extends HashMap<String, Set<String>>{

    public void add(String name, String phoneNumber) {
        Set<String> phonesByName = this.get(name);
        if (phonesByName == null){
            phonesByName = new HashSet<>();
        }
        phonesByName.add(phoneNumber);
        this.put(name, phonesByName);
    }
}
