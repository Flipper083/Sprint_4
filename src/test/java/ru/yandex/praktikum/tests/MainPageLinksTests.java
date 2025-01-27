package ru.yandex.praktikum.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

/** Класс тестов для проверки корректности ссылок на главной странице*/

public class MainPageLinksTests {
    private WebDriver webDriver;

    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    // Ожидаемый URL для логотипа "Яндекс"
    private final String yandexUrl = "//yandex.ru";
    // Ожидаемый URL для логотипа "Самокат"
    private final String scooterUrl = "//qa-scooter.praktikum-services.ru";

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @After
    public void tearDown() {
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    /** Тест для проверки корректности ссылки на логотип "Яндекс"*/

    @Test
    public void checkYandexLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);
        // Проверка, что ссылка на логотип "Яндекс" ведет на ожидаемый URL
        assertTrue(
                "Yandex Logo Link doesn't go to " + this.yandexUrl,
                mainPage.getYandexLogoLink().contains(this.yandexUrl)
        );
        // Проверка, что ссылка на логотип "Яндекс" открывается в новой вкладке
        assertTrue(
                "Yandex Logo Link doesn't open in new tab",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    /**Тест для проверки корректности ссылки на логотип "Самокат"*/

    @Test
    public void checkScooterLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);
        // Проверка, что ссылка на логотип "Самокат" ведет на ожидаемый URL
        assertTrue(
                "Scooter Logo Link doesn't go to " + this.scooterUrl,
                mainPage.getScooterLogoLink().contains(this.scooterUrl)
        );
    }
}