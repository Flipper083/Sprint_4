package ru.yandex.praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobjects.MainPage;
import pageobjects.OrderPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

/** Класс тестов для проверки процесса оформления заказа*/

@RunWith(Parameterized.class)
public class OrderPageTests {
    private WebDriver webDriver;

    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    // Переменные для данных, необходимых для оформления заказа
    private final String name, surname, address, metro, phone, date, term, color, comment;
    // Ожидаемое сообщение об успешном оформлении заказа
    private final String expectedOrderSuccessText = "Заказ оформлен";

    /** Конструктор класса OrderPageTests */
    public OrderPageTests(
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String term,
            String color,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    /** Параметры для запуска теста*/
    @Parameterized.Parameters(name = "Оформление заказа. Позитивный сценарий. Пользователь: {0} {1}")
    public static Object[][] setDataForOrder() {
        return new Object[][] {
                {"Ольга", "Николаевна", "Москва, ул. Тестовая, д. 8, кв. 34", "Сокол", "81234585890", "27.01.2025", "четверо суток", "чёрный жемчуг", "Коммент!"},
                {"Иван ", "Иванов", "Москва, ул. Таганская, д. 2, кв. 45", "Улица Скобелевская", "89875543210", "27.01.2025", "трое суток", "серая безысходность", "Привезите в первой половине дня"},
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.firefoxdriver().setup(); // Используем FirefoxDriver
        this.webDriver = new FirefoxDriver(); // Инициализация FirefoxDriver
        this.webDriver.get(mainPageUrl);
    }

    @After
    public void tearDown() {
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    /**Тест для проверки процесса оформления заказа после нажатия на кнопку "Заказать" в шапке*/
    @Test
    public void orderWithHeaderButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton(); // Нажатие на кнопку принятия куки
        mainPage.clickOrderButtonHeader(); // Нажатие на кнопку "Заказать" в шапке
        orderPage.makeOrder(this.name, this.surname, this.address, this.metro, this.phone, this.date, this.term, this.color, this.comment); // Оформление заказа
        // Проверка, что сообщение об успешном оформлении заказа соответствует ожидаемому
        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    /** Тест для проверки процесса оформления заказа после нажатия на кнопку "Заказать" в теле сайта*/
    @Test
    public void orderWithBodyButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonBody(); // Нажатие на кнопку "Заказать" в теле сайта
        orderPage.makeOrder(this.name, this.surname, this.address, this.metro, this.phone, this.date, this.term, this.color, this.comment); // Оформление заказа
        // Проверка, что сообщение об успешном оформлении заказа соответствует ожидаемому
        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }
}