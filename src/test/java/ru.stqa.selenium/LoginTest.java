package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginTest {
    private WebDriver driver;

    @BeforeEach
    public void start(){
        driver = new ChromeDriver();
    }

    @Test
    public void registrationTest()  {

        String password = "1234qwert";
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.linkText("New customers click here")).click();
        driver.findElement(By.name("firstname")).sendKeys("Alina");
        driver.findElement(By.name("lastname")).sendKeys("Ivanova");
        driver.findElement(By.name("address1")).sendKeys("Sovetskay");
        driver.findElement(By.name("address2")).sendKeys("Center");
        driver.findElement(By.name("postcode")).sendKeys("101000");
        driver.findElement(By.name("city")).sendKeys("Moscow");

        WebElement selectElem = driver.findElement(By.name("country_code"));
        Select selectCountry = new Select(selectElem);
        selectCountry.selectByValue("RU");

        driver.findElement(By.name("email")).sendKeys("alina_ivanova@mail.ru");
        driver.findElement(By.name("phone")).sendKeys("89203459812");
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("confirmed_password")).sendKeys(password);
        driver.findElement(By.name("create_account")).click();
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
