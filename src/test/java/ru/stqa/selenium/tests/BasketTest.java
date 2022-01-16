package ru.stqa.selenium.tests;

import org.junit.jupiter.api.Test;

public class BasketTest extends TestBase {
    private int EXPECTED_NUMBER_PRODUCTS = 3;

    @Test
    public void workWithBasketTest() {
        app.addProducts(EXPECTED_NUMBER_PRODUCTS);
        app.checkBasket(EXPECTED_NUMBER_PRODUCTS);
        app.clearBasket(EXPECTED_NUMBER_PRODUCTS);
    }

}
