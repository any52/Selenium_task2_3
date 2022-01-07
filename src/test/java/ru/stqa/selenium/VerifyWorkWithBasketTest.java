package ru.stqa.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class VerifyWorkWithBasketTest {
    private WebDriver driver;
    private int EXPECTED_NUMBER_PRODUCTS = 3;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @Test
    public void basketTest()  {
        for(int i =0; i < EXPECTED_NUMBER_PRODUCTS; i++){
            addProduct(driver);
       }
        checkBasketAndRemoveProducts(driver, EXPECTED_NUMBER_PRODUCTS);
    }

    public void addProduct(WebDriver driver){
        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = new ArrayList<>();
        products = driver.findElements(By.cssSelector(".product"));
        products.get(0).click();
        WebElement initialItems = driver.findElement(By.cssSelector("#cart span"));
        int initialNumberProducts = Integer.parseInt(initialItems.getText());
        checkSizePresence(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);
        driver.findElement(By.cssSelector("[name = add_cart_product]")).click();
        wait.until(textToBe(By.cssSelector("#cart span"), String.valueOf(initialNumberProducts + 1)));
    }

    public void checkBasketAndRemoveProducts(WebDriver driver, int expectedNumberProducts){
        driver.findElement(By.partialLinkText("Checkout")).click();
        List<WebElement> allRowNumbers = driver.findElements(By.cssSelector("#box-checkout-summary tr"));
        allRowNumbers.remove(0); // Remove redundant elements
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        int actualNumberProducts = 0;
        for (int i = 0; i < allRowNumbers.size(); i ++) {
            int quantity = Integer.parseInt(allRowNumbers.get(i).findElement(By.cssSelector("td:nth-child(1)")).getText());
            actualNumberProducts += quantity;
        }
        Assertions.assertEquals(expectedNumberProducts , actualNumberProducts );

        List <WebElement> removeProductButtons = new ArrayList<>();
        for (int i =0; i < EXPECTED_NUMBER_PRODUCTS; i++){
            removeProductButtons = driver.findElements(By.cssSelector("[name = remove_cart_item]"));
            if (removeProductButtons.size() > 0) {
                WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);
                WebElement line  = driver.findElement(By.cssSelector("#box-checkout-summary tr:nth-child(2)"));
                removeProductButtons.get(0).click();
                wait.until(stalenessOf(line));
                wait.until(numberOfElementsToBe((By.cssSelector("[name = remove_cart_item]")), (removeProductButtons.size() - 1)));
            }
        }
    }

    public void checkSizePresence(WebDriver driver ){
            if (driver.findElements(By.cssSelector("[name = 'options[Size]']")).size() > 0){
                Select sizeSelect = new Select(driver.findElements(By.cssSelector("[name = 'options[Size]']")).get(0));
                sizeSelect.selectByValue("Small");
            }
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
