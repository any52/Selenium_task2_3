package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

public class ProductPage extends Page{

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSizePresence( ){
        if (driver.findElements(By.cssSelector("[name = 'options[Size]']")).size() > 0){
            Select sizeSelect = new Select(driver.findElements(By.cssSelector("[name = 'options[Size]']")).get(0));
            sizeSelect.selectByValue("Small");
            return true;
        }
        return false;
    }

    public void addProduct(int initialNumberProducts){
        WebDriverWait wait = new WebDriverWait(driver, 10/*seconds*/);
        driver.findElement(By.cssSelector("[name = add_cart_product]")).click();
        wait.until(textToBe(By.cssSelector("#cart span"), String.valueOf(initialNumberProducts + 1)));
    }

    public void moveToBasket(){
        driver.findElement(By.partialLinkText("Checkout")).click();
    }
}
