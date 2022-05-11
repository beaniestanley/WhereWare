package at.tugraz.software22.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.Product;
import at.tugraz.software22.ui.viewmodel.ProductViewModel;

@RunWith(MockitoJUnitRunner.class)
public class ProductViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WherewareApplication applicationMock;

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
    public void givenOrderServiceWithTwoSprints_whenLoadData_thenVerifyProductsLiveDataChanged() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(12, 3, "Superproduct", "Storage 5"));
        expectedProducts.add(new Product(5, 2, "Topseller", "Storage 2"));

        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedProducts);

        productViewModel.loadData();

        Mockito.verify(productsLiveDataObserver).onChanged(expectedProducts);
    }
}
