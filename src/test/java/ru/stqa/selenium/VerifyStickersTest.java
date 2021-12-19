package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyStickersTest {

    private WebDriver driver;
    private int EXPECTED_STICKER = 1;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void stickersTest()  {

        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = new ArrayList<>();
        List<WebElement> stickers = new ArrayList<>();
        products = driver.findElements(By.cssSelector("[class = 'product column shadow hover-light']"));
        for (int i = 0; i < products.size(); i ++){
            stickers = products.get(i).findElements(By.cssSelector("[class *= 'sticker']"));
            Assertions.assertEquals(EXPECTED_STICKER, stickers.size());
        }
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
