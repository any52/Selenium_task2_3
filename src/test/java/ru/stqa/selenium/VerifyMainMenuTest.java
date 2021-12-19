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

public class VerifyMainMenuTest {

    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void mainMenuTest()  {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> pointsOfMenu = new ArrayList<>();
        List<WebElement> subpointsOfMenu  = new ArrayList<>();
        pointsOfMenu = driver.findElements(By.id ("app-"));
        for (int i = 0; i < pointsOfMenu.size(); i ++){
            pointsOfMenu.get(i).click();
            Assertions.assertTrue(checkTitleOfPage());
            pointsOfMenu = driver.findElements(By.id ("app-"));
            subpointsOfMenu = pointsOfMenu.get(i).findElements(By.className ("name"));
            if (subpointsOfMenu.size()>0){
                for (int j = 0; j < subpointsOfMenu.size(); j ++){
                    subpointsOfMenu.get(j).click();
                    Assertions.assertTrue(checkTitleOfPage());
                    pointsOfMenu = driver.findElements(By.id ("app-"));
                    subpointsOfMenu = pointsOfMenu.get(i).findElements(By.className ("name"));
                }
            }
        }

    }
    public boolean checkTitleOfPage(){
        if((driver.findElements(By.cssSelector("#content h1")).size() == 0)){
            return false;
        };
        return true;
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
