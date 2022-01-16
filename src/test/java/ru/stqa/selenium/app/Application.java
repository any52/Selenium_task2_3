package ru.stqa.selenium.app;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.selenium.pages.BasketPage;
import ru.stqa.selenium.pages.MainPage;
import ru.stqa.selenium.pages.ProductPage;

public class Application {

    private WebDriver driver;

    private BasketPage basketPage;
    private MainPage mainPage;
    private ProductPage productPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        basketPage = new BasketPage(driver);
        productPage = new ProductPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void addProducts(int expectedNumberProducts){
        for (int i = 0; i < expectedNumberProducts; i++) {
            mainPage.open().clickFirstProduct();
            productPage.isSizePresence();
            productPage.addProduct(mainPage.getItems());
        }

    }

    public void checkBasket(int expectedNumberProducts){
        productPage.moveToBasket();
        Assertions.assertEquals(expectedNumberProducts , basketPage.getNumberProductsInBasket() );
    }

    public void clearBasket(int expectedNumberProducts){
        basketPage.clear(expectedNumberProducts);
    }

}
