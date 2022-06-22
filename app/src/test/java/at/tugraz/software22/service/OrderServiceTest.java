package at.tugraz.software22.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Product;

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
    public void givenProduct_whenRemoveProduct_thenVerifyRemoveGetsCalled() {
        Integer expectedQuantity = 4;
        Integer expectedTime = 2;
        String expectedName = "Ipad";
        String expectedLocation = "Aisle 3";

        orderService.addProduct(expectedName, expectedTime, expectedLocation, expectedQuantity);

        Mockito.verify(orderRepositoryMock, Mockito.times(1)).addProduct(expectedName, expectedTime, expectedLocation, expectedQuantity);

        orderService.removeProduct(1);

        Mockito.verify(orderRepositoryMock, Mockito.times(1)).deleteProduct(1);

    }

    @Test
    public void givenRepositoryWithOneProduct_whenGetProduct_thenReturnListWithOneProduct() {
        List<Product> expectedProducts = Collections.singletonList(new Product(3, 7, "Basketball", "aisle 5", 1));
        Mockito.when(orderRepositoryMock.getProducts()).thenReturn(expectedProducts);

        List<Product> actualProducts = orderService.getProducts();
        Assert.assertEquals(1, actualProducts.size());
    }

    @Test
    public void givenProduct_whenAddProduct_thenVerifyAddGetsCalled() {
        orderService.addProduct("Toy", 1, "aisle 1", 2);
        Mockito.verify(orderRepositoryMock, Mockito.times(1)).addProduct("Toy", 1, "aisle 1", 2);
    }

    @Test
    public void givenProduct_whenUpdateProduct_thenVerifyUpdateGetsCalled() {
        orderService.addProduct("Basketball", 3, "aisle 5", 7);
        Mockito.verify(orderRepositoryMock, Mockito.times(1)).addProduct("Basketball", 3, "aisle 5", 7);
        orderService.updateProduct(1,"Football", 3, "aisle 5", 7);
        Mockito.verify(orderRepositoryMock, Mockito.times(1)).updateProduct(1,"Football", 3, "aisle 5", 7);
    }

    //TODO afterwards for changing the status of products and orders :)
//    @Test
//    public void givenRepositoryWithOneOrder_whenStateChanged_thenReturnOneOrder() {
//        List<Order> expectedOrders = Arrays.asList(new Order(1, 1, Collections.emptyList(), 1));
//        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);
//        orderService.changeStateOfOrder(1, "finished");
//        List<Order> actualOrders = orderService.getAll();
//        Assert.assertEquals(Statuses.FINISHED, actualOrders.get(0).getStatus());
//
//        orderService.changeStateOfOrder(1, "pending");
//        Assert.assertEquals(Statuses.PENDING, actualOrders.get(0).getStatus());
//
//        orderService.changeStateOfOrder(1, "started");
//        Assert.assertEquals(Statuses.STARTED, actualOrders.get(0).getStatus());
//    }
//    @Test
//    public void givenRepositoryWithOneOrder_whenStateChangedOfProduct_thenReturnOneOrderWithCorrectState() {
//        List<Order> expectedOrders = Arrays.asList(new Order(1, 1,
//                Arrays.asList(new Product(1, 1, "Xbox One", "Aisle 3", 1)), 1));
//        Mockito.when(orderRepositoryMock.getAll()).thenReturn(expectedOrders);
//        orderService.changeStateOfProductInOrder(1,1, "finished");
//        List<Order> actualOrders = orderService.getAll();
//        Assert.assertEquals(Statuses.FINISHED, actualOrders.get(0).getAllOrders_().get(0).getStatus());
//
//        orderService.changeStateOfProductInOrder(1,1, "pending");
//        Assert.assertEquals(Statuses.PENDING, actualOrders.get(0).getAllOrders_().get(0).getStatus());
//
//        orderService.changeStateOfProductInOrder(1,1, "started");
//        Assert.assertEquals(Statuses.STARTED, actualOrders.get(0).getAllOrders_().get(0).getStatus());
//    }


}