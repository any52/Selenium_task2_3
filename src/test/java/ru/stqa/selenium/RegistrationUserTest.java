package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class RegistrationUserTest {
    private WebDriver driverChrome;
    private WebDriver driverFirefox;
    private WebDriver driverEdge;

    @BeforeEach
    public void start(){
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driverFirefox = new FirefoxDriver();
        driverFirefox.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driverEdge = new EdgeDriver();
        driverEdge.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void registrationUserTest()  {
        checkRegistrationUser(driverChrome);
        checkRegistrationUser(driverFirefox);
        checkRegistrationUser(driverEdge);
    }

    public void checkRegistrationUser(WebDriver driver){
        String password = "1234qwert";
        String randomIndex  = String.valueOf(Math.random());
        String email = "irina_ivanova" +randomIndex + "@mail.ru";
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        driver.findElement(By.name("firstname")).sendKeys("Irina");
        driver.findElement(By.name("lastname")).sendKeys("Ivanova");
        driver.findElement(By.name("address1")).sendKeys("Arizona1");
        driver.findElement(By.name("address2")).sendKeys("Arizona2");
        driver.findElement(By.name("postcode")).sendKeys("20107");
        driver.findElement(By.name("city")).sendKeys("Finics");

        WebElement selectElemCountry = driver.findElement(By.cssSelector(".selection span b"));
        selectElemCountry.click();
        WebElement search = driver.findElement(By.cssSelector("[type = search]"));
        search.sendKeys("United States" + Keys.ENTER);

        WebElement selectElemZone = driver.findElement(By.cssSelector(".content tr:nth-child(5) td:nth-child(2) select"));
        Select selectZone = new Select(selectElemZone);
        selectZone.selectByValue("AZ");

        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("phone")).sendKeys("89203459812");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.name("create_account")).click();

        driver.findElement(By.linkText("Logout")).click();

        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();

        driver.findElement(By.linkText("Logout")).click();
    }

    @AfterEach
    public void stop(){
        driverChrome.quit();
        driverChrome = null;
        driverFirefox.quit();
        driverFirefox = null;
        driverEdge.quit();
        driverEdge = null;
    }
}
