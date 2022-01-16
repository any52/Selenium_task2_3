package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class BasketPage extends Page {

    public BasketPage(WebDriver driver) {
        super(driver);
    }

    public int getNumberProductsInBasket(){

        List<WebElement> allRowNumbers = driver.findElements(By.cssSelector("#box-checkout-summary tr"));
        allRowNumbers.remove(0); // Remove redundant elements
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        allRowNumbers.remove(allRowNumbers.size()-1);
        int actualNumberProducts = 0;
        int quantity = 0;
        for (int i = 0; i < allRowNumbers.size(); i ++) {
            quantity = Integer.parseInt(allRowNumbers.get(i).findElement(By.cssSelector("td:nth-child(1)")).getText());
            actualNumberProducts += quantity;
        }
        return actualNumberProducts;
    }

    public void clear(int expectedNumberProducts){
        List <WebElement> removeProductButtons = new ArrayList<>();
        for (int i =0; i < expectedNumberProducts; i++){
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
}
