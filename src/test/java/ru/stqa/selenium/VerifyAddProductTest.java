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

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VerifyAddProductTest {

    private WebDriver driver;

    @BeforeEach
    public void start(){

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void addProductTest()  {
        String absolutePath = new File("").getAbsolutePath();
        String absolutePathToImage = absolutePath + "\\Img.jpg";
        String nameProduct = "Basketball";

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.cssSelector("#box-apps-menu li:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#content div a:nth-child(2)")).click();
        driver.findElements(By.cssSelector("[name = status]")).get(0).click();//
        driver.findElement(By.cssSelector("[name ='name[en]']")).sendKeys(nameProduct);
        driver.findElement(By.cssSelector("[name = code]")).sendKeys("100678");
        Select defaultCategorySelect = new Select(driver.findElement(By.cssSelector("[name = default_category_id]")));
        defaultCategorySelect.selectByValue("0");
        driver.findElements(By.cssSelector("[name = 'product_groups[]']")).get(2).click();
        driver.findElement(By.cssSelector("[name = quantity]")).clear();
        driver.findElement(By.cssSelector("[name = quantity]")).sendKeys("1");
        Select quantityUnitSelect = new Select(driver.findElement(By.cssSelector("[name = quantity_unit_id]")));
        quantityUnitSelect.selectByValue("1");
        Select deliveryStatusSelect = new Select(driver.findElement(By.cssSelector("[name = delivery_status_id]")));
        deliveryStatusSelect.selectByValue("1");
        Select soldOutStatusSelect =  new Select(driver.findElement(By.cssSelector("[name = sold_out_status_id]")));
        soldOutStatusSelect.selectByValue("2");
        driver.findElement(By.cssSelector("[name = 'new_images[]']")).sendKeys((CharSequence) absolutePathToImage);
        driver.findElement(By.cssSelector("[name = date_valid_from]")).sendKeys("20102021");
        driver.findElement(By.cssSelector("[name = date_valid_to]")).sendKeys("20102026");

        driver.findElement(By.cssSelector(".tabs li:nth-child(2)")).click();
        Select menufactureSelect = new Select(driver.findElement(By.cssSelector("[name = manufacturer_id]")));
        menufactureSelect.selectByValue("1");
        driver.findElement(By.cssSelector("[name = keywords]")).sendKeys("Games, sport products");
        driver.findElement(By.cssSelector("[name = 'short_description[en]']"))
                .sendKeys("Basketball ball");
        driver.findElement(By.className("trumbowyg-editor")).sendKeys("Special basketball ball for game");
        driver.findElement(By.cssSelector("[name = 'head_title[en]']"))
                .sendKeys("Basketball ball");
        driver.findElement(By.cssSelector("[name = 'meta_description[en]']"))
                .sendKeys("Meta description");

        driver.findElement(By.cssSelector(".tabs li:nth-child(4)")).click();
        driver.findElement(By.cssSelector("[name = purchase_price]")).clear();
        driver.findElement(By.cssSelector("[name = purchase_price]")).sendKeys("10");
        Select currencySelect =  new Select(driver.findElement(By.cssSelector("[name = purchase_price_currency_code]")));
        currencySelect.selectByValue("USD");
        Select taxClassSelect = new Select(driver.findElement(By.cssSelector("[name  = tax_class_id]")));
        taxClassSelect.selectByValue("");
        driver.findElement(By.cssSelector("[name = 'prices[USD]']")).sendKeys("10");
        driver.findElement(By.cssSelector("[name = 'prices[EUR]']")).sendKeys("8");

        driver.findElement(By.cssSelector("[name = save]")).click();

        List<WebElement> products = driver.findElements(By.className("row"));
        boolean result = false;
        for (WebElement product : products) {
           if (product.getText().equals(nameProduct)) {
                result = true;
            }
        }

        Assertions.assertTrue(result);
    }

    @AfterEach
    public void stop(){
        driver.quit();
        driver = null;
    }
}
