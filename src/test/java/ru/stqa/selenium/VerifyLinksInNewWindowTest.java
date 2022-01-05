package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class VerifyLinksInNewWindowTest {

    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void addProductTest()  {

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.className("button")).click();
        List <WebElement> links = driver.findElements(By.cssSelector("[class = 'fa fa-external-link']"));

        String mainWindow = driver.getWindowHandle();
        for (int i = 0; i < links.size(); i++){
            Set<String> oldWindows = driver.getWindowHandles();
            links.get(i).click();
            WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);
            wait.until((WebDriver d) -> d.getWindowHandles().size() > oldWindows.size());
            Set<String> newWindows = driver.getWindowHandles();
            newWindows.removeAll(oldWindows);
            if (newWindows.size() == 1) {
                String newWindowIndex = newWindows.stream().findFirst().get();
                driver.switchTo().window(newWindowIndex);
                driver.close();
                driver.switchTo().window(mainWindow);
            }
        }

    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
