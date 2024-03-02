package pageobject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class HomePage {
    private WebDriver driver;

    // Лист с вопросами о важном
    private By questionsListAccordion = By.className("accordion__item");

    // Кнопка "Заказать" сверху
    private By orderButtonFirst = By.className("Button_Button__ra12g");

    // Кнокпка "Заказать" в середине
    private By orderButtonSecond = By.xpath(".//div[@class='Home_RoadMap__2tal_']/div[5]/button");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnOrderFirstButton() {
        driver.findElement(orderButtonFirst).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void clickOnOrderSecondButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(orderButtonSecond));
        driver.findElement(orderButtonSecond).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void checkTitle() {
        Assert.assertEquals(driver.findElement(orderButtonFirst).getText(), "Заказать");
    }

    public void checkFAQ(String expectedQuestion, String expectedAnswer) {
        int questionListSize = driver.findElements(questionsListAccordion).size();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, "
                + "document.documentElement.clientHeight));");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        for (int i = 0; i < questionListSize; i++) {
            if (i == (questionListSize - 1)) {
                js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, "
                        + "document.body.scrollHeight, document.documentElement.clientHeight));");
            }
            String headerClassName = "accordion__heading-" + i;
            WebElement question = driver.findElement(By.id(headerClassName));
            String actualQuestion = question.getText();
            String panelClassName = "accordion__panel-" + i;
            if (actualQuestion.equals(expectedQuestion)) {
                question.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelClassName)));
                WebElement answer = driver.findElement(By.id(panelClassName));
                String actualAnswer = answer.getText();
                Assert.assertEquals(expectedAnswer, actualAnswer);
            }
            break;
        }
    }
}
