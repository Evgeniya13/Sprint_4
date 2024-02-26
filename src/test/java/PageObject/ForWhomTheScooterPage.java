package PageObject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class ForWhomTheScooterPage {

    private WebDriver driver;

    // Заголовок Для кого самокат
    private By title = By.className("Order_Header__BZXOb");

    // Поле Имя
    private By firstNameField = By.xpath(".//input[contains(@placeholder, 'Имя')]");

    // Поле Фамилия
    private By familyNameField = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");

    // Поле Адресс
    private By addressField = By.xpath(".//input[contains(@placeholder, 'Адрес')]");

    // Поле Станция метро
    private By stationField = By.xpath(".//input[contains(@placeholder, 'Станция метро')]");

    // Лист станций метро
    private By stationList = By.className("select-search__select");

    // Поле Телефон
    private By phoneField = By.xpath(".//input[contains(@placeholder, 'Телефон')]");

    // Кнопка Далее
    private By nextButton = By.xpath(".//button[text()='Далее']");

    public ForWhomTheScooterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectStation(String station) {
        driver.findElement(stationField).click();
        driver.findElement(stationField).sendKeys(station);
        driver.findElement(stationList).click();
    }

    public void fillDataForWhomScooter(String firstName, String familyName, String address, String station, String phone) {
        Assert.assertEquals(driver.findElement(title).getText(), "Для кого самокат");
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(familyNameField).clear();
        driver.findElement(familyNameField).sendKeys(familyName);
        driver.findElement(addressField).clear();
        driver.findElement(addressField).sendKeys(address);
        selectStation(station);
        driver.findElement(phoneField).clear();
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickOnNextButton() {
        driver.findElement(nextButton).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }
}