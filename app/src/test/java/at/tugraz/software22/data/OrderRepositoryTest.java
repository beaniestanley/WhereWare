package at.tugraz.software22.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;


public class OrderRepositoryTest{

    @Test
    public void  givenEmptyRepository_whenGetAll_thenReturnFourDummyOrders(){

        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        List<Order> ordersActual = dummyOrderRepository.getAll();

        int expectedSize = 4;
        Assert.assertEquals(expectedSize, ordersActual.size());

    }
    @Test
    public void  givenEmptyRepository_whenGet_getProductQuantity() {
        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        List<Order> ordersActual = dummyOrderRepository.getAll();

        Product firstProduct = new Product(200, 1, "Xbox One", "Aisle 1",1);
        Product secondProduct = new Product(100, 2, "Xbox Controller", "Aisle 2",2);
        Product thirdProduct = new Product(50, 1, "Xbox HDMI Adapter", "Aisle 3",3);
        Product fourthProduct = new Product(250, 1, "Nintendo Switch", "Aisle 1",4);
        Product fifthProduct = new Product(350, 2, "PS4 PRO", "Aisle 2",5);
        Product sixthProduct = new Product(150, 1, "PS5 SLIM", "Aisle 1",6);

        List<Product> productsOrderOne = new ArrayList<>(Arrays.asList(firstProduct, secondProduct, thirdProduct));
        List<Product> productsOrderTwo = new ArrayList<>(Arrays.asList(fifthProduct, firstProduct, sixthProduct));
        List<Product> productsOrderThree = new ArrayList<>(Arrays.asList(fifthProduct, fourthProduct, sixthProduct));

        Order expectedFirstOrder = new Order(4,  productsOrderOne, 1);
        Order expectedSecondOrder = new Order(4, productsOrderTwo, 2);
        Order expectedThirdOrder = new Order(4,productsOrderThree, 3);

        expectedFirstOrder.finishOrder();

        Assert.assertEquals(expectedFirstOrder.getProductQuantity_(), ordersActual.get(0).getProductQuantity_());
        Assert.assertEquals(expectedFirstOrder.getEstimatedTime_(), ordersActual.get(0).getEstimatedTime_());
        Assert.assertEquals(expectedFirstOrder.getStatus(), ordersActual.get(0).getStatus());
        Assert.assertEquals(expectedFirstOrder.getAllProducts_().size(), ordersActual.get(0).getAllProducts_().size());

        Assert.assertEquals(expectedSecondOrder.getProductQuantity_(), ordersActual.get(1).getProductQuantity_());
        Assert.assertEquals(expectedSecondOrder.getEstimatedTime_(), ordersActual.get(1).getEstimatedTime_());
        Assert.assertEquals(expectedSecondOrder.getStatus(), ordersActual.get(1).getStatus());
        Assert.assertEquals(expectedSecondOrder.getAllProducts_().size(), ordersActual.get(1).getAllProducts_().size());

        Assert.assertEquals(expectedThirdOrder.getProductQuantity_(), ordersActual.get(2).getProductQuantity_());
        Assert.assertEquals(expectedThirdOrder.getEstimatedTime_(), ordersActual.get(2).getEstimatedTime_());
        Assert.assertEquals(expectedThirdOrder.getStatus(), ordersActual.get(2).getStatus());
        Assert.assertEquals(expectedThirdOrder.getAllProducts_().size(), ordersActual.get(2).getAllProducts_().size());

    }

    @Test
    public void  givenRepositoryWithDefaultSize_whenGetProduct_thenReturnListWithDefaultSize() {
        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        List<Product> actualProducts = dummyOrderRepository.getProducts();

        int defaultSize = 6;
        Assert.assertEquals(defaultSize, actualProducts.size());
    }

    @Test
    public void givenRepositoryWithDefaultSize_whenAddProduct_thenReturnProductListWithSizeSeven() {
        String expectedName = "Charger";
        Integer expectedQuantity = 9;
        String expectedLocation = "aisle 6";
        Integer expectedTime = 3;

        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        dummyOrderRepository.addProduct(expectedName, expectedTime, expectedLocation, expectedQuantity);

        List<Product> actualProducts = dummyOrderRepository.getProducts();

        int expectedSize = 7;
        Assert.assertEquals(expectedSize, actualProducts.size());
    }

    @Test
    public void givenRepositoryWithDefaultSize_whenDeleteProduct_thenReturnProductListWithSizeFive() {
        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        dummyOrderRepository.deleteProduct(1);

        List<Product> actualProducts = dummyOrderRepository.getProducts();

        int expectedSize = 5;
        Assert.assertEquals(expectedSize, actualProducts.size());
    }

    @Test
    public void givenRepositoryWithDefaultSize_whenUpdated_thenReturnUpdatedList() {
        String expectedName = "Shirt";
        String expectedLocation = "aisle 1";
        Integer expectedQuantity = 70;
        Integer expectedTime = 1;

        DummyOrderRepository dummyOrderRepository = new DummyOrderRepository();

        dummyOrderRepository.addProduct(expectedName, expectedTime, expectedLocation, expectedQuantity);

        String updatedName = "T-Shirt";
        dummyOrderRepository.updateProduct(7, updatedName, expectedTime, expectedLocation, expectedQuantity);

        List<Product> actualProduct = dummyOrderRepository.getProducts();

        int expectedSize = 7;
        Assert.assertEquals(expectedSize, actualProduct.size());
        Assert.assertEquals(updatedName, actualProduct.get(6).getName());
    }
}