package ru.stqa.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class VerifyPageProductTest {
    private WebDriver driverChrome;
    private WebDriver driverFirefox;
    private WebDriver driverEdge;
    private String EXPECTED_DECORATION_USUAL_PRICE = "line-through";
    private Integer EXPECTED_DECORATION_PROMOTIONAL_PRICE = 700;  //I prefer that bold font has value 700 or more
    private String GREY_COLOR = "GREY";
    private String RED_COLOR =  "RED";

    @BeforeEach
    public void start(){

        driverChrome = new ChromeDriver();
        driverFirefox = new FirefoxDriver();
        driverEdge = new EdgeDriver();
        driverChrome.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driverFirefox.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driverEdge.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void pageProductTest()  {
        checkPageProduct(driverChrome);
        checkPageProduct(driverFirefox);
        checkPageProduct(driverEdge);
    }

    public void checkPageProduct(WebDriver driver){

        driver.get("http://localhost/litecart/en/");
        String nameOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .name"))
                .getAttribute("textContent");
        String usualPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .regular-price"))
                .getAttribute("textContent");
        String  promotionalPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .campaign-price"))
                .getAttribute("textContent");
        String colorUsualPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .regular-price"))
                .getCssValue("color");
        String  colorPromotionalPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .campaign-price"))
                .getCssValue("color");
        String decorUsualPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .regular-price"))
                .getCssValue("text-decoration");
        String  decorPromotionalPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .campaign-price"))
                .getCssValue("font-weight");
        String sizeUsualPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .regular-price"))
                .getCssValue("font-size");
        String  sizePromotionalPriceOnMainPage = driver.findElement(By.cssSelector("#box-campaigns .campaign-price"))
                .getCssValue("font-size");

        driver.findElement(By.cssSelector("#box-campaigns .name")).click();

        String nameOnProductPage = driver.findElement(By.cssSelector("h1.title"))
                .getAttribute("textContent");
        String usualPriceOnProductPage = driver.findElement(By.cssSelector(".regular-price"))
                .getAttribute("textContent");
        String  promotionalPriceOnProductPage = driver.findElement(By.cssSelector(".campaign-price"))
                .getAttribute("textContent");
        String colorUsualPriceOnProductPage = driver.findElement(By.cssSelector(".regular-price"))
                .getCssValue("color");
        String  colorPromotionalPriceOnProductPage = driver.findElement(By.cssSelector(".campaign-price"))
                .getCssValue("color");
        String decorUsualPriceOnProductPage = driver.findElement(By.cssSelector(".regular-price"))
                .getCssValue("text-decoration");
        String  decorPromotionalPriceOnProductPage = driver.findElement(By.cssSelector(".campaign-price"))
                .getCssValue("font-weight");
        String sizeUsualPriceOnProductPage = driver.findElement(By.cssSelector(".regular-price"))
                .getCssValue("font-size");
        String  sizePromotionalPriceOnProductPage = driver.findElement(By.cssSelector(".campaign-price"))
                .getCssValue("font-size");

        Assertions.assertEquals(nameOnMainPage, nameOnProductPage);

        Assertions.assertEquals(usualPriceOnMainPage, usualPriceOnProductPage);
        Assertions.assertEquals(promotionalPriceOnMainPage, promotionalPriceOnProductPage);

        checkIsColorStartWithRGB(colorUsualPriceOnMainPage, driver);
        checkIsColorStartWithRGB(colorUsualPriceOnProductPage, driver);
        checkIsColorStartWithRGB(colorPromotionalPriceOnMainPage,driver);
        checkIsColorStartWithRGB(colorPromotionalPriceOnProductPage, driver);

        checkColor(colorUsualPriceOnMainPage, driver, GREY_COLOR);
        checkColor(colorUsualPriceOnProductPage, driver, GREY_COLOR);
        checkColor(colorPromotionalPriceOnMainPage, driver, RED_COLOR);
        checkColor(colorPromotionalPriceOnProductPage, driver, RED_COLOR);

        Assertions.assertTrue(decorUsualPriceOnMainPage.startsWith(EXPECTED_DECORATION_USUAL_PRICE));
        Assertions.assertTrue(decorUsualPriceOnProductPage.startsWith(EXPECTED_DECORATION_USUAL_PRICE));
        Assertions.assertTrue( Integer.parseInt(decorPromotionalPriceOnMainPage) >= EXPECTED_DECORATION_PROMOTIONAL_PRICE);
        Assertions.assertTrue(Integer.parseInt(decorPromotionalPriceOnProductPage) >= EXPECTED_DECORATION_PROMOTIONAL_PRICE);

        checkSizePrices(sizeUsualPriceOnMainPage, sizeUsualPriceOnProductPage);
        checkSizePrices(sizePromotionalPriceOnMainPage, sizePromotionalPriceOnProductPage);
    }

    public void checkIsColorStartWithRGB(String color, WebDriver driver){
        if (driver instanceof ChromeDriver || driver instanceof EdgeDriver) {
            Assertions.assertTrue(color.startsWith("rgba"));
        }
        if (driver instanceof FirefoxDriver) {
            Assertions.assertTrue(color.startsWith("rgb"));
        }
    }

    public void checkColor(String colorValue, WebDriver driver, String color){
        String colorWithoutRGBa= " ";
        if (driver instanceof ChromeDriver || driver instanceof EdgeDriver) {
            colorWithoutRGBa = colorValue.substring(5);
        }
        if (driver instanceof FirefoxDriver) {
            colorWithoutRGBa = colorValue.substring(4, (colorValue.length()-1));
        }
        String[] rGBa = colorWithoutRGBa.split(",");
        int rColor = Integer.parseInt(rGBa[0].trim());
        int gColor = Integer.parseInt(rGBa[1].trim());
        int bColor = Integer.parseInt(rGBa[2].trim());
        if(color.equals("GREY")) {
            Assertions.assertEquals(rColor, gColor);
            Assertions.assertEquals(rColor, bColor);
        }
        if(color.equals("RED")){
            Assertions.assertEquals(gColor, 0);
            Assertions.assertEquals(bColor, 0);
        }
    }

    public  void checkSizePrices(String sizeUsualPrice, String sizePromotionalPrice) {
        if (sizeUsualPrice.endsWith("px") && sizePromotionalPrice.endsWith("px")) {
            String sizeUsualPriceReplaced = sizeUsualPrice.replace("px", " ");
            String sizePromotionalPriceReplaced = sizePromotionalPrice.replace("px", " ");
            int sizeUsualPriceInt = Integer.parseInt(sizeUsualPriceReplaced.trim().substring(0,2));
            int sizePromotionalPriceInt = Integer.parseInt(sizePromotionalPriceReplaced.trim());
            Assertions.assertTrue(sizePromotionalPriceInt > sizeUsualPriceInt);
        }
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
