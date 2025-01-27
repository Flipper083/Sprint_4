package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Класс, описывающий главную страницу приложения */

public class MainPage {
    private final WebDriver webDriver;
    // Локаторы для элементов на главной странице
    private final By accordionHeaders = By.className("accordion__heading"); // Заголовки аккордеона
    // Элементы аккордеона
    private final By accordionItems = By.xpath(".//div[@class='accordion__panel']/p");
    // Кнопка оформления заказа в шапке
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");
    // Кнопка оформления заказа в теле сайта
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");
    // Кнопка принятия куки
    private final By cookieAcceptButton = By.id("rcc-confirm-button");
    // Логотип Яндекса
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]");
    // Логотип Самоката
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]");

    /**Конструктор класса MainPage*/

    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    /**Метод для ожидания загрузки элемента аккордеона*/

    public void waitForLoadItem(int index) {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.accordionItems).get(index)));
    }

    /**Метод для нажатия на кнопку "Принять куки"*/

    public void clickOnCookieAcceptButton() {
        this.webDriver.findElement(this.cookieAcceptButton).click();
    }

    /**Метод для получения текста заголовка аккордеона*/

    public String getAccordionHeaderText(int index) {
        return this.webDriver.findElements(this.accordionHeaders).get(index).getText();
    }

    /**Метод для получения текста элемента аккордеона*/

    public String getAccordionItemText(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).getText();
    }

    /**Метод для нажатия на заголовок аккордеона*/

    public void clickAccordionHeader(int index) {
        this.webDriver.findElements(this.accordionHeaders).get(index).click();
    }

    /**Метод для проверки, отображается ли элемент аккордеона*/

    public boolean isAccordionItemDisplayed(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).isDisplayed();
    }

    /**Метод для нажатия на кнопку оформления заказа в шапке*/

    public void clickOrderButtonHeader() {
        this.webDriver.findElement(this.orderButtonHeader).click();
    }

    ///Метод для нажатия на кнопку оформления заказа в теле сайта
    public void clickOrderButtonBody() {
        this.webDriver.findElement(this.orderButtonBody).click();
    }

    /**Метод для получения ссылки логотипа Яндекса*/

    public String getYandexLogoLink() {
        return this.webDriver.findElement(this.yandexLogoLink).getAttribute("href");
    }

    /**Метод для получения ссылки логотипа Самоката*/

    public String getScooterLogoLink() {
        return this.webDriver.findElement(this.scooterLogoLink).getAttribute("href");
    }

    /**Метод для проверки, открывается ли ссылка логотипа Яндекса в новой вкладке*/

    public boolean isYandexLogoLinkOpenedInNewTab() {
        String blanc = "_blank";
        String value = this.webDriver.findElement(this.yandexLogoLink).getAttribute("target");
        return blanc.equals(value);
    }
}