package at.tugraz.software22.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.WhereWareApplication;

@RunWith(MockitoJUnitRunner.class)
public class ProductViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WhereWareApplication applicationMock;

    @Mock
    private OrderService orderServiceMock;

    @Mock
    private Observer<List<Product>> productsLiveDataObserver;

    private ProductViewModel productViewModel;

    @Before
    public void setUp() {
        Mockito.when(applicationMock.getOrderService()).thenReturn(orderServiceMock);
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);

        productViewModel = new ProductViewModel(applicationMock);

        productViewModel.getOrderLiveData().observeForever(productsLiveDataObserver);
    }

    @Test
    public void givenOrderServiceWithTwoProducts_whenLoadData_thenVerifyProductsLiveDataChanged() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(12, 3, "Superproduct", "Storage 5", 1));
        expectedProducts.add(new Product(5, 2, "Topseller", "Storage 2", 2));
        Order order = new Order(2, expectedProducts, 1);

        Mockito.when(orderServiceMock.getOrder(1)).thenReturn(order);

        productViewModel.loadData(1);

        Mockito.verify(productsLiveDataObserver).onChanged(expectedProducts);
    }

    @Test
    public void givenOrderServiceWithTwoProducts_whenLoadProductData_thenVerifyProductsLiveDataChanged() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(12, 3, "Superproduct", "Storage 5", 1));
        expectedProducts.add(new Product(5, 2, "Topseller", "Storage 2", 2));

        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProducts);

        productViewModel.loadProductData();

        Mockito.verify(productsLiveDataObserver).onChanged(expectedProducts);
    }

    @Test
    public void givenOrderServiceWithOneProduct_whenRemoveData_thenVerifyProductsLiveDataChanged() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(12, 3, "Superproduct", "Storage 5", 1));

        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProducts);

        productViewModel.loadProductData();
        productViewModel.removeProduct(1);

        Mockito.verify(productsLiveDataObserver, Mockito.times(2)).onChanged(expectedProducts);
    }

    @Test
    public void givenOrderServiceWithOneProduct_whenUpdateData_thenVerifyUserLiveDataChanged() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(2, 4, "TestProduct", "aisle 9", 1));
        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProducts);

        productViewModel.loadProductData();
        productViewModel.updateProduct(1,"Pillow", 2, "aisle 9", 4);
        Mockito.verify(productsLiveDataObserver, Mockito.times(2)).onChanged(expectedProducts);
    }

    @Test
    public void givenOrderServiceWithNoProduct_whenCreateData_thenVerifyUserLiveDataChanged() {
        List<Product> expectedProducts = Collections.emptyList();

        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProducts);

        productViewModel.loadProductData();
        productViewModel.createProduct("TestProduct", 4, "aisle 9", 2);

        Mockito.verify(productsLiveDataObserver, Mockito.times(2)).onChanged(expectedProducts);
    }
}
