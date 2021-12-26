package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifySortingCountriesAdminTest {

    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void sortingCountryZonesTest() {
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> countryWebElements = new ArrayList<>();
        List<WebElement> zoneNamesWebElements = new ArrayList<>();
        List<String> sortedZoneNames = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        countryWebElements = driver.findElements(By.cssSelector(".row td:nth-child(3) a"));
        for (int i = 0; i <  countryWebElements.size(); i ++){
            countryWebElements.get(i).click();
            zoneNamesWebElements = driver.findElements(By.cssSelector("#content td:nth-child(3) select"));
            for(int j =0; j < zoneNamesWebElements.size(); j++) {
                Select selectZones = new Select(zoneNamesWebElements.get(j));
                zoneNames.add(selectZones.getFirstSelectedOption().getAttribute("textContent"));
                sortedZoneNames.add(selectZones.getFirstSelectedOption().getAttribute("textContent"));
            }
            Collections.sort(sortedZoneNames);
            Assertions.assertEquals(sortedZoneNames, zoneNames);
            zoneNames.clear();
            sortedZoneNames.clear();
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
            countryWebElements = driver.findElements(By.cssSelector(".row td:nth-child(3) a"));
        }
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
