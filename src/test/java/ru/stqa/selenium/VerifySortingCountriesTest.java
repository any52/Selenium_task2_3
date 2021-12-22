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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifySortingCountriesTest {

    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void sortingCountriesTest()  {

        login();
        List<WebElement> countryWebElements = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<String> sortedCountries = new ArrayList<>();
        countryWebElements = driver.findElements(By.cssSelector(".row td:nth-child(5)"));
        for (int i = 0; i < countryWebElements.size(); i ++){
            countries.add(countryWebElements.get(i).getAttribute("textContent"));
            sortedCountries.add(countryWebElements.get(i).getAttribute("textContent"));
        }
        Collections.sort(sortedCountries);
        Assertions.assertEquals(sortedCountries, countries);
    }

    @Test
    public void sortingCountryZonesTest() {
        login();
        List<WebElement> numberZonesWebElements = new ArrayList<>();
        List<WebElement> zoneNameWebElements = new ArrayList<>();
        List<String> sortedZoneNames = new ArrayList<>();
        List<String> zoneNames = new ArrayList<>();
        List<WebElement> countryWebElements = new ArrayList<>();
        numberZonesWebElements = driver.findElements(By.cssSelector(".row td:nth-child(6)"));
        for (int i = 0; i < numberZonesWebElements.size(); i ++){
            int numberZones = Integer.parseInt(numberZonesWebElements.get(i).getAttribute("textContent"));
            if(numberZones > 0 ){
                countryWebElements = driver.findElements(By.cssSelector(".row td:nth-child(5) a"));
                countryWebElements.get(i).click();
                zoneNameWebElements = driver.findElements(By.cssSelector("#table-zones td:nth-child(3)"));
                for(int j =0; j < zoneNameWebElements.size(); j++){
                    zoneNames.add(zoneNameWebElements.get(j).getAttribute("textContent"));
                    sortedZoneNames.add(zoneNameWebElements.get(j).getAttribute("textContent"));
                }
                zoneNames.remove(zoneNames.size()-1);
                sortedZoneNames.remove(sortedZoneNames.size()-1);
                Collections.sort(sortedZoneNames);
                Assertions.assertEquals(sortedZoneNames, zoneNames);
                zoneNames.clear();
                sortedZoneNames.clear();
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
                numberZonesWebElements = driver.findElements(By.cssSelector(".row td:nth-child(6)"));
            }
        }
    }

    public void login(){
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }

}
