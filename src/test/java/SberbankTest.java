import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SberbankTest {

    private WebDriver driver;

    private static String SBERBANK = "http://www.sberbank.ru/ru/person";
    private static String REGION = "//*[@class='hd-ft-region__title'][1]";
    private static String SEARCH = "//*[@class][@type='search']";
    private static String NOVGOROD = "//*[@class][text()='Нижегородская область']";
    private static String ICONS = "//*[@class='footer__social_item']";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testSberbank() throws Exception {
        driver.get(SBERBANK);

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(REGION))));
        driver.findElement(By.xpath(REGION)).click();

        driver.findElement(By.xpath(SEARCH)).sendKeys("Нижегородская область");
        driver.findElement(By.xpath(NOVGOROD)).click();
        Assert.assertEquals("Нижегородская область",
                driver.findElement(By.xpath(REGION)).getText());
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        List<WebElement> icons = driver.findElements(By.xpath(ICONS));
        Assert.assertTrue(icons.size() == 6);
    }

    @After
    public void finish() {
        driver.quit();
    }
}
