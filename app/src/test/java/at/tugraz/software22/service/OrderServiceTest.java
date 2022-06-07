package at.tugraz.software22.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepositoryMock;

    private OrderService orderService;

    @Before
    public void setUp() {
        orderService = new OrderService(orderRepositoryMock);
    }

    @Test
    public void givenEmptyRepository_whenGetAll_thenReturnNoOrders() {
        List<Order> expectedOrders = Collections.emptyList();
        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getAll();
        Assert.assertEquals(0, actualOrders.size());
    }

    @Test
    public void givenRepositoryWithOneOrder_whenGetAll_thenReturnOneOrder() {
        List<Order> expectedOrders = Collections.singletonList(new Order(1, Collections.emptyList(), 1));
        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getAll();
        Assert.assertEquals(1, actualOrders.size());
    }

    @Test
    public void givenRepositoryWithOneOrderAndOneProduct_whenGetAll_thenReturnOneOrderWithOneProduct() {
        Integer expectedQuantity = 1;
        Integer expectedID = 1;
        Integer expectedTime = 1;
        String expectedName = "Xbox One";
        String expectedLocation = "Aisle 3";
        List<Order> expectedOrders = Collections.singletonList(new Order(expectedQuantity,
                Collections.singletonList(new Product(expectedTime, expectedQuantity, expectedName, expectedLocation, expectedID)), 1));
        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getAll();
        Assert.assertEquals(1, actualOrders.size());
        Assert.assertEquals(expectedQuantity, actualOrders.get(0).getProductQuantity_());
        Assert.assertEquals(expectedTime, actualOrders.get(0).getEstimatedTime_());
        Assert.assertEquals(expectedID, actualOrders.get(0).getId());
        //Asserts for Product
        Assert.assertEquals(expectedQuantity, actualOrders.get(0).getAllProducts_().get(0).getProductQuantity());
        Assert.assertEquals(expectedTime, actualOrders.get(0).getAllProducts_().get(0).getEstimatedTime());
        Assert.assertEquals(expectedName, actualOrders.get(0).getAllProducts_().get(0).getName());
        Assert.assertEquals(expectedLocation, actualOrders.get(0).getAllProducts_().get(0).getLocation());
    }

    @Test
    public void givenRepositoryWithOneOrder_whenStateChangedOfProduct_thenReturnOneOrderWithCorrectState() {
        List<Order> expectedOrders = Arrays.asList(new Order( 1,
                Arrays.asList(new Product(1, 1, "Xbox One", "Aisle 3", 1)), 1));
        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);
        //orderService.tickProduct(1,1);
        List<Order> actualOrders = orderService.getAll();
        Assert.assertEquals(Statuses.FINISHED, actualOrders.get(0).getAllProducts_().get(0).getStatus());

        //orderService.tickProduct(1,1);
        Assert.assertEquals(Statuses.PENDING, actualOrders.get(0).getAllProducts_().get(0).getStatus());

    }


}