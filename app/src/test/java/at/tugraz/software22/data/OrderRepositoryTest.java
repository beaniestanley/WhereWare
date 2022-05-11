package at.tugraz.software22.data;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;


public class OrderRepositoryTest{

    @Test
    public void  givenEmptyRepository_whenGetAll_thenReturnFourDummyOrders(){

        OrderRepository orderRepository = new OrderRepository();

        List<Order> ordersActual = orderRepository.getOrders();

        int expectedSize = 3;
        Assert.assertEquals(expectedSize, ordersActual.size());

    }
    @Test
    public void  givenEmptyRepository_whenGet_getProductQuantity() {
        OrderRepository orderRepository = new OrderRepository();

        List<Order> ordersActual = orderRepository.getOrders();

        Product firstProduct = new Product(200, 1, "Xbox One", "Aisle 1");
        Product secondProduct = new Product(100, 2, "Xbox Controller", "Aisle 2");
        Product thirdProduct = new Product(50, 1, "Xbox HDMI Adapter", "Aisle 3");
        Product fourthProduct = new Product(250, 1, "Nintendo Switch", "Aisle 1");
        Product fithProduct = new Product(350, 2, "PS4 PRO", "Aisle 2");
        Product sixthProduct = new Product(150, 1, "PS5 SLIM", "Aisle 1");

        List<Product> productsOrderOne = new ArrayList<>(Arrays.asList(firstProduct, secondProduct, thirdProduct));
        List<Product> productsOrderTwo = new ArrayList<>(Arrays.asList(fithProduct, firstProduct, sixthProduct));
        List<Product> productsOrderThree = new ArrayList<>(Arrays.asList(fithProduct, fourthProduct, sixthProduct));


        Order expectedFirstOrder = new Order(4, 350, productsOrderOne);
        Order expectedSecondOrder = new Order(4,700, productsOrderTwo);
        Order expectedThirdOrder = new Order(4,750, productsOrderThree);

        Assert.assertEquals(expectedFirstOrder.getProductQuantity_(), ordersActual.get(0).getProductQuantity_());
        Assert.assertEquals(expectedFirstOrder.getEstimatedTime_(), ordersActual.get(0).getEstimatedTime_());
        Assert.assertEquals(expectedFirstOrder.getStatus(), ordersActual.get(0).getStatus());
        Assert.assertEquals(expectedFirstOrder.getAllOrders_().size(), ordersActual.get(0).getAllOrders_().size());

        Assert.assertEquals(expectedSecondOrder.getProductQuantity_(), ordersActual.get(1).getProductQuantity_());
        Assert.assertEquals(expectedSecondOrder.getEstimatedTime_(), ordersActual.get(1).getEstimatedTime_());
        Assert.assertEquals(expectedSecondOrder.getStatus(), ordersActual.get(1).getStatus());
        Assert.assertEquals(expectedSecondOrder.getAllOrders_().size(), ordersActual.get(1).getAllOrders_().size());

        Assert.assertEquals(expectedThirdOrder.getProductQuantity_(), ordersActual.get(2).getProductQuantity_());
        Assert.assertEquals(expectedThirdOrder.getEstimatedTime_(), ordersActual.get(2).getEstimatedTime_());
        Assert.assertEquals(expectedThirdOrder.getStatus(), ordersActual.get(2).getStatus());
        Assert.assertEquals(expectedThirdOrder.getAllOrders_().size(), ordersActual.get(2).getAllOrders_().size());

    }
}