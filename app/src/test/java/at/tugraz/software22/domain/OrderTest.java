package at.tugraz.software22.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderTest{

    @Test
    public void givenNewOrder_whenConstructor_thenReturnNewOrder(){
        Integer expectedQuantity = 0;
        Integer expectedTime = 0;
        Statuses expectedStatus = Statuses.PENDING;
        List<Product> expectedOrders = new ArrayList<>();

        Order firstOrder = new Order();

        Assert.assertEquals(expectedQuantity, firstOrder.getProductQuantity_());
        Assert.assertEquals(expectedTime, firstOrder.getEstimatedTime_());
        Assert.assertEquals(expectedStatus, firstOrder.getStatus());
        Assert.assertEquals(expectedOrders, firstOrder.getAllProducts_());
    }

    @Test
    public void givenNewOrderWithParameters_whenConstructor_thenReturnNewOrder(){
        Integer expectedQuantity = 0;
        Integer expectedTime = 0;
        Integer expectedID = 1;
        Statuses expectedStatus = Statuses.PENDING;
        List<Product> expectedOrders = new ArrayList<>(Collections.singletonList(new Product(1, 1, "Xbox One", "Aisle 3", expectedID)));

        Order firstOrder = new Order(expectedQuantity, expectedTime, expectedOrders, 1);

        Assert.assertEquals(expectedQuantity, firstOrder.getProductQuantity_());
        Assert.assertEquals(expectedTime, firstOrder.getEstimatedTime_());
        Assert.assertEquals(expectedStatus, firstOrder.getStatus());
        Assert.assertEquals(expectedOrders, firstOrder.getAllProducts_());
    }

}