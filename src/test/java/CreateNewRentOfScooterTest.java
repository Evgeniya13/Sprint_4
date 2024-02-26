import PageObject.AboutTheRentPage;
import PageObject.ForWhomTheScooterPage;
import PageObject.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class CreateNewRentOfScooterTest {
    private final String URL = "https://qa-scooter.praktikum-services.ru/";
    private WebDriver driver;
    private final String firstName;
    private final String familyName;
    private final String address;
    private final String station;
    private final String phone;
    private final String period;
    private final String colorOfScooter;
    private final String comment;
    private final String browserName;

    public CreateNewRentOfScooterTest(String browserName, String firstName, String familyName, String address,
                                      String station, String phone, String period, String colorOfScooter, String comment) {

        this.firstName = firstName;
        this.familyName = familyName;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.period = period;
        this.colorOfScooter = colorOfScooter;
        this.comment = comment;
        this.browserName = browserName;
    }

    @Parameterized.Parameters
    public static Object[][] getDataForRental() {
        return new Object[][]{
                {"firefox", "Иван", "Иванов", "Проспект Вернадского 88-1", "Юго-Западная", "+79161234567", "трое суток",
                        "чёрный жемчуг", "Привет!"},
                {"chrome", "Петр", "Петров", "Большая Садовая, 302-бис, 5 этаж, кв. 50", "Маяковская", "+79874561230",
                        "четверо суток", "серая безысходность", "Пароль: Воланд"},
        };
    }

    @Before
    public void setWebDriver() {
        driver = createDriver(this.browserName);
        driver.manage().window().maximize();
        driver.get(URL);
        Cookie newCookie = new Cookie("Cartoshka", "true");
        Cookie newCookie2 = new Cookie("Cartoshka-legacy", "true");
        driver.manage().addCookie(newCookie);
        driver.manage().addCookie(newCookie2);
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void checkFAQ() {
        HomePage homepage = new HomePage(driver);
        homepage.checkFAQ();
    }

    @Test
    public void createNewOrder() {
        HomePage homepage = new HomePage(driver);
        homepage.clickOnOrderButton();
        ForWhomTheScooterPage forWhomTheScooterPage = new ForWhomTheScooterPage(driver);
        forWhomTheScooterPage.fillDataForWhomScooter(firstName, familyName, address, station, phone);
        forWhomTheScooterPage.clickOnNextButton();
        AboutTheRentPage aboutTheRentPage = new AboutTheRentPage(driver, period, colorOfScooter);
        aboutTheRentPage.fillDataAboutRent(comment);
        aboutTheRentPage.confirmAndCreateRent();
    }

    @After
    public void teardown() {
        // Закрыть браузер
        driver.quit();
    }

    private static WebDriver createDriver(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver-win64\\chromedriver.exe");
            // Создаем экземпляр ChromeDriver
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\WebDriver\\bin\\firefox\\geckodriver.exe");
            // Создаем экземпляр FirefoxDriver
            driver = new FirefoxDriver();
        } else {
            System.out.println("Неподдерживаемый браузер: " + browser);
        }
        return driver;
    }
}
