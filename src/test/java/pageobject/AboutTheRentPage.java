package pageobject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

import org.hamcrest.MatcherAssert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AboutTheRentPage {
    private WebDriver driver;

    // Цвет самоката
    private String colorOfScooter;

    // Период аренды
    private String period;

    // Заголовок Про аренду
    private By titleName = By.className("Order_Header__BZXOb");

    // Поле Дата
    private By dateField = By.xpath(".//div[@class='react-datepicker-wrapper']/div/input");

    // Кнопка следующий месяц в date picker
    private By nextMonthButton = By.xpath(".//div[@class='react-datepicker']/button[@class='react-datepicker__navigation react-datepicker__navigation--next']");

    // Кнопка день первой недели следующего месяца
    private By dayButton = By.xpath(".//div[@class='react-datepicker__week']/*[1]");

    // Поле Период аренды
    private By rentalPeriodField = By.className("Dropdown-placeholder");

    // Лист периода аренды
    private By rentalPeriodList;

    // Чекбокс с Цветом самоката
    private By colorCheckbox;

    // Поле Комментарий
    private By commentField = By.xpath(".//input[contains(@placeholder, 'Комментарий')]");

    // Кнопка Заказать
    private By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Кнопка Да во всплывающем окне
    private By yesInPopUp = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[2]/button[2]");

    // Заказ оформлен во всплывающем окне
    private By orderIsReady = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[@class='Order_ModalHeader__3FDaJ']");

    public AboutTheRentPage(WebDriver driver, String period, String colorOfScooter) {
        this.driver = driver;
        this.period = period;
        this.colorOfScooter = colorOfScooter;
        this.rentalPeriodList = By.xpath(".//div[@class='Dropdown-menu']/div[contains(text(), '" + this.period + "')]");
        this.colorCheckbox = By.xpath(".//div[@class='Order_Checkboxes__3lWSI']/*[contains(text(), '" + this.colorOfScooter + "')]");
    }

    // Для ввода даты можно было бы использовать метод sendKeys,
    // но так как дата должна быть в будущем, а тесты должны запускаться постоянно,
    // то было принято решение выбирать 1 день следующего месяца
    public void selectDate() {
        driver.findElement(dateField).click();
        driver.findElement(nextMonthButton).click();
        driver.findElement(dayButton).click();
    }

    public void selectRentalPeriod() {
        driver.findElement(rentalPeriodField).click();
        driver.findElement(rentalPeriodList).click();
    }

    public void fillDataAboutRent(String comment) {
        Assert.assertEquals(driver.findElement(titleName).getText(), "Про аренду");
        selectDate();
        selectRentalPeriod();
        driver.findElement(colorCheckbox).click();
        driver.findElement(commentField).clear();
        driver.findElement(commentField).sendKeys(comment);
    }

    public void confirmAndCreateRent() {
        WebDriverWait wait = new WebDriverWait(driver, 3);
        driver.findElement(orderButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(yesInPopUp));
        driver.findElement(yesInPopUp).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderIsReady));
        MatcherAssert.assertThat(driver.findElement(orderIsReady).getText(), containsString("Заказ оформлен"));
    }
}
