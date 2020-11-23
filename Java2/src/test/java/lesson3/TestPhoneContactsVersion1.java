package lesson3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Тесты использования телефонного справочника
 *
 * {@link PhoneContactsVersion1}
 */
public class TestPhoneContactsVersion1 {

    private PhoneContactsVersion1 phoneContacts;

    @Before
    public void before(){
        phoneContacts = new PhoneContactsVersion1();
        phoneContacts.add("Петров", "+7-000-000-00-01");
        phoneContacts.add("Иванов", "+7-000-000-00-02");
        phoneContacts.add("Сидоров", "+7-000-000-00-03");
        phoneContacts.add("Лукин", "+7-000-000-00-04");
        phoneContacts.add("Соколов", "+7-000-000-00-05");
        phoneContacts.add("Мясников", "+7-000-000-00-06");
        phoneContacts.add("Петров", "+7-000-000-00-07");
        phoneContacts.add("Иванов", "+7-000-000-00-08");
        phoneContacts.add("Петров", "+7-000-000-00-09");
        phoneContacts.add("Мясников", "+7-000-000-00-10");
        phoneContacts.add("Коклюшкин", "+7-000-000-00-11");
        phoneContacts.add("Иванов", "+7-000-000-00-12");

        System.out.println("Преднаполненный справочник: " + phoneContacts.toString());
    }

    @Test
    public void testGet()
    {
        Set<String> ivanov = phoneContacts.get("Иванов");

        Assert.assertNotNull(ivanov);
        Assert.assertEquals(3, ivanov.size());
        Assert.assertTrue(ivanov.contains("+7-000-000-00-02"));
        Assert.assertTrue(ivanov.contains("+7-000-000-00-08"));
        Assert.assertTrue(ivanov.contains("+7-000-000-00-12"));

        Set<String> petrosyan = phoneContacts.get("Петросян");

        Assert.assertNotNull(petrosyan);
        Assert.assertEquals(0, petrosyan.size());
    }

    @Test
    public void testAdd()
    {
        phoneContacts.add("Иванов", "+7-000-000-00-12");
        Set<String> ivanov = phoneContacts.get("Иванов");

        Assert.assertNotNull(ivanov);
        Assert.assertEquals(3, ivanov.size());
        Assert.assertFalse(ivanov.contains("+7-000-000-00-13"));

        phoneContacts.add("Иванов", "+7-000-000-00-13");
        ivanov = phoneContacts.get("Иванов");

        Assert.assertNotNull(ivanov);
        Assert.assertEquals(4, ivanov.size());
        Assert.assertTrue(ivanov.contains("+7-000-000-00-13"));

        phoneContacts.add("Петросян", "+7-000-000-00-14");
        Set<String> petrosyan = phoneContacts.get("Петросян");

        Assert.assertNotNull(petrosyan);
        Assert.assertEquals(1, petrosyan.size());
        Assert.assertTrue(petrosyan.contains("+7-000-000-00-14"));

        ivanov = phoneContacts.get("Иванов");
        Assert.assertFalse(ivanov.contains("+7-000-000-00-14"));
    }
}
