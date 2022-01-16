package ru.stqa.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page{
    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    public void clickFirstProduct(){
        if (driver.findElements(By.cssSelector(".product")).size() > 0){
            driver.findElements(By.cssSelector(".product")).get(0).click();
        }
    }

    public int getItems(){
        return Integer.parseInt(driver.findElement(By.cssSelector("#cart span")).getText());
    }
}
