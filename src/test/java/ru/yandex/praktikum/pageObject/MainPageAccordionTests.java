package ru.yandex.praktikum.pageObject;

import pageObjects.MainPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

/**Класс тестов для проверки работы и содержимого раскрывающегося блока "Вопросы о важном" */


@RunWith(Parameterized.class)
public class MainPageAccordionTests {

    private WebDriver webDriver;


    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";

    // Порядковый номер элемента аккордеона
    private final int numberOfElement;
    // Ожидаемый текст в заголовке элемента аккордеона
    private final String expectedHeaderText;
    // Ожидаемый текст в раскрывающемся блоке элемента аккордеона
    private final String expectedItemText;

    /**Конструктор класса MainPageAccordionTests */

    public MainPageAccordionTests(int numberOfAccordionItem, String expectedHeaderText, String expectedItemText) {
        this.numberOfElement = numberOfAccordionItem; // Инициализация номера элемента
        this.expectedHeaderText = expectedHeaderText; // Инициализация ожидаемого текста заголовка
        this.expectedItemText = expectedItemText; // Инициализация ожидаемого текста элемента
    }

    /** Параметры для запуска теста */

    @Parameterized.Parameters(name = "Текст в блоке\"Вопросы о важном\". Проверяемый элемент: {1}")
    public static Object[][] setTestData() {
        return new Object[][] {
                // Параметры для тестов: номер элемента, ожидаемый заголовок, ожидаемый текст
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." }, // bug here з -> в
        };
    }

    @Before
    public void startUp() {
        // Настройка драйвера перед запуском теста
        WebDriverManager.chromedriver().setup(); // Установка драйвера Chrome
        this.webDriver = new ChromeDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @After
    public void tearDown() {
        // Закрытие веб-драйвера после завершения теста
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    /** Тест для проверки работы аккордеона и для проверки текста в заголовках и в раскрывающихся блоках */

    @Test
    public void checkAccordionIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver); // Создание экземпляра главной страницы

        mainPage.clickOnCookieAcceptButton(); // Нажатие на кнопку принятия куки

        mainPage.clickAccordionHeader(this.numberOfElement); // Нажатие на заголовок аккордеона
        mainPage.waitForLoadItem(this.numberOfElement); // Ожидание загрузки элемента аккордеона

        // Проверка, отображается ли элемент
        if (mainPage.isAccordionItemDisplayed(this.numberOfElement)) {
            // Проверка текста в заголовке аккордеона
            MatcherAssert.assertThat("Problems with text in accordion header #" + this.numberOfElement,
                    this.expectedHeaderText,
                    equalTo(mainPage.getAccordionHeaderText(this.numberOfElement))
            );
            // Проверка текста в раскрывающемся блоке аккордеона
            MatcherAssert.assertThat("Problems with text in accordion item #" + this.numberOfElement,
                    this.expectedItemText,
                    equalTo(mainPage.getAccordionItemText(this.numberOfElement))
            );
        }
        else {
            // Если элемент не загрузился, тест завершается с ошибкой
            fail("Accordion header item #" + this.numberOfElement + "didn't load");
        }
    }
}