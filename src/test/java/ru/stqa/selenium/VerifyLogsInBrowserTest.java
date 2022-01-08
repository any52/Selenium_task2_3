package ru.stqa.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyLogsInBrowserTest {
    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @RepeatedTest(10)
    public void checkLogsBrowserTest()  {

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElements(By.cssSelector(".row td:nth-child(3) a")).get(2).click(); // Open Subcategory
        List<WebElement> products =  findProducts();
        for (int i = 0; i < products.size(); i++) {
            List<LogEntry> logBeforeClick = driver.manage().logs().get("browser").getAll();
            products.get(i).click();
            List<LogEntry> logAfterClick = driver.manage().logs().get("browser").getAll();
            Assertions.assertEquals(logBeforeClick, logAfterClick);
            driver.navigate().back();
            products = findProducts();
        }
    }

    public List<WebElement> findProducts(){
        List<WebElement> products = new ArrayList<>();
        products = driver.findElements(By.cssSelector(".row td:nth-child(3) a"));
        products.remove(0); //Remove redundant elements
        products.remove(0);
        products.remove(0);
        return products;
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
