import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class InsuranceTest {

    private WebDriver driver;

    private static String RGS = "http://www.rgs.ru";
    private static String MENU = "//*[@data-toggle='dropdown' and @class]";
    private static String DMS = "//*[contains(@href,'dms/generalinfo')][1]";
    private static String SENDREQUEST = "//*[@data-form='insuranceApplication'][1]";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testInsurance() throws Exception {

        driver.get(RGS);

        driver.findElement(By.xpath(MENU)).click();

        driver.findElement(By.xpath(DMS)).click();

        Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(SENDREQUEST))));

        Assert.assertEquals("ДМС — добровольное медицинское страхование",
                driver.findElement(By.xpath("//*[@class='content-document-header']")).getText());

        driver.findElement(By.xpath(SENDREQUEST)).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));

        Assert.assertEquals("Заявка на добровольное медицинское страхование",
                driver.findElement(By.xpath("//h4[@class='modal-title']")).getText());

        //Заполнение полей
        driver.findElement(By.name("LastName")).sendKeys("Иванов");
        driver.findElement(By.name("FirstName")).sendKeys("Иван");
        driver.findElement(By.name("MiddleName")).sendKeys("Иванович");
        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Челябинская область");
        WebElement phone = driver.findElement(By.xpath("//*[contains(@data-bind,'Phone')]"));
        phone.click();
        phone.sendKeys("1231231010");
        driver.findElement(By.name("Email")).sendKeys("WrongWrongEmail");
        WebElement date = driver.findElement(By.name("ContactDate"));
        date.click();
        date.sendKeys("10102019");

        //Отправить заявку
        driver.findElement(By.className("checkbox")).click();
        driver.findElement(By.id("button-m")).click();

        Assert.assertEquals("Введите адрес электронной почты",
                driver.findElement(By.xpath("//*[text()[contains(.,'почты')]]")).getText());
    }

    @After
    public void finish() {
        driver.quit();
    }
}
