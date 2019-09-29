import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SberbankTest {

    private WebDriver driver;

    private static String SBERBANK = "http://www.sberbank.ru/ru/person";
    private static String REGION = "//*[@id='main']/div[1]/div/div/div[4]/header/div/div/div/div[2]/div[3]/div/div/a/div/span";
    private static String SEARCH = "/html/body/div[7]/div/div/div/div/div/div/div/div[1]/div/div/input";
    private static String NOVGOROD = "/html/body/div[7]/div/div/div/div/div/div/div/div[3]/div[1]/a";
    private static String ICONS = "//*[@class='footer__social_item']";

    private static List<String> list = new ArrayList<>();
    static {
        list.add("fb");
        list.add("tw");
        list.add("yt");
        list.add("ins");
        list.add("vk");
        list.add("ok");
    }

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
