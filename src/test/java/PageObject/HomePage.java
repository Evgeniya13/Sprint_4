package PageObject;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class HomePage {
    private WebDriver driver;

    // Вопросы из "Вопросы о важном"
    private String[] questionsList = {"Сколько это стоит? И как оплатить?", "Хочу сразу несколько самокатов! Так можно?",
            "Как рассчитывается время аренды?", "Можно ли заказать самокат прямо на сегодня?",
            "Можно ли продлить заказ или вернуть самокат раньше?", "Вы привозите зарядку вместе с самокатом?",
            "Можно ли отменить заказ?", "Я жизу за МКАДом, привезёте?"};

    // Ответы из "Вопросы о важном"
    private String[] answersList = {"Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать "
                    + "несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды "
                    + "начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная "
                    + "аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без "
                    + "передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."};

    // Лист с вопросами о важном
    private By questionsListAccordion = By.className("accordion__item");

    // Кнопка "Заказать" сверху
    private By orderButtonFirst = By.className("Button_Button__ra12g");

    // Кнокпка "Заказать" в середине
    private By OrderButtonSecond = By.className("Button_Button__ra12g Button_Middle__1CSJM");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnOrderButton() {
        driver.findElement(orderButtonFirst).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void checkTitle() {
        Assert.assertEquals(driver.findElement(orderButtonFirst).getText(), "Заказать");
    }

    public void checkFAQ() {
        int questionListSize = driver.findElements(questionsListAccordion).size();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, "
                + "document.documentElement.clientHeight));");
        for (int i = 0; i < questionListSize; i++) {
            if (i == (questionListSize - 1)) {
                js.executeScript("window.scrollTo(0, Math.max(document.documentElement.scrollHeight, "
                        + "document.body.scrollHeight, document.documentElement.clientHeight));");
            }
            String headerClassName = "accordion__heading-" + i;
            String panelClassName = "accordion__panel-" + i;
            WebElement question = driver.findElement(By.id(headerClassName));
            Assert.assertEquals(questionsList[i], question.getText());
            question.click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement answer = driver.findElement(By.id(panelClassName));
            Assert.assertEquals(answersList[i], answer.getText());
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }
}
