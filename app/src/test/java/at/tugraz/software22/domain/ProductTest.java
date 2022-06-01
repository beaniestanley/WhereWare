package at.tugraz.software22.domain;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest{
    @Test
    public void givenNewProduct_whenConstructor_thenRetrunNewProduct(){
        Integer expectedQuantity = 0;
        Integer expectedTime = 0;
        Integer expectedId = 1;
        String expectedName = "Xbox One";
        String expectedLocation = "Aisle 3";
        Statuses expectedStatus = Statuses.PENDING;

        Product firstProduct = new Product(expectedTime, expectedQuantity, expectedName, expectedLocation, expectedId);

        Assert.assertEquals(expectedId, firstProduct.getId());
        Assert.assertEquals(expectedQuantity, firstProduct.getProductQuantity());
        Assert.assertEquals(expectedTime, firstProduct.getEstimatedTime());
        Assert.assertEquals(expectedLocation, firstProduct.getLocation());
        Assert.assertEquals(expectedStatus, firstProduct.getStatus());
        Assert.assertEquals(expectedName, firstProduct.getName());
    }
    @Test
    public void givenTwoNewProduct_whenConstructor_thenRetrunTwoNewProduct(){
        Integer expectedQuantity = 0;
        Integer expectedTime = 0;
        Integer expectedId = 1;
        Integer expectedSecondId = 2;
        String expectedName = "Xbox One";
        String expectedLocation = "Aisle 3";
        Statuses expectedStatus = Statuses.PENDING;

        Product firstProduct = new Product(expectedTime, expectedQuantity, expectedName, expectedLocation, expectedId);
        Product secondProduct = new Product(expectedTime, expectedQuantity, expectedName, expectedLocation, expectedSecondId);

        Assert.assertEquals(expectedId, firstProduct.getId());
        Assert.assertEquals(expectedQuantity, firstProduct.getProductQuantity());
        Assert.assertEquals(expectedTime, firstProduct.getEstimatedTime());
        Assert.assertEquals(expectedLocation, firstProduct.getLocation());
        Assert.assertEquals(expectedStatus, firstProduct.getStatus());
        Assert.assertEquals(expectedName, firstProduct.getName());

        Assert.assertEquals(expectedSecondId, secondProduct.getId());
        Assert.assertEquals(expectedQuantity, secondProduct.getProductQuantity());
        Assert.assertEquals(expectedTime, secondProduct.getEstimatedTime());
        Assert.assertEquals(expectedLocation, secondProduct.getLocation());
        Assert.assertEquals(expectedStatus, secondProduct.getStatus());
        Assert.assertEquals(expectedName, secondProduct.getName());
    }
}