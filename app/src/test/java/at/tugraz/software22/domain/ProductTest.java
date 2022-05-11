package at.tugraz.software22.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductTest{
    @Test
    public void givenNewProduct_whenConstructor_thenRetrunNewProduct(){
        Integer expectedQuantity = 0;
        Integer expectedTime = 0;
        String expectedName = "Xbox One";
        String expectedLocation = "Aisle 3";
        String expectedStatus = "pending";

        Product firstProduct = new Product(expectedTime, expectedQuantity, expectedName, expectedLocation);

        Assert.assertEquals(expectedQuantity, firstProduct.getProductQuantity());
        Assert.assertEquals(expectedTime, firstProduct.getEstimatedTime());
        Assert.assertEquals(expectedLocation, firstProduct.getLocation());
        Assert.assertEquals(expectedStatus, firstProduct.getStatus());
        Assert.assertEquals(expectedName, firstProduct.getName());
    }
}