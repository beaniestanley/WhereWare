package at.tugraz.software22.ui.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderViewModelTest {
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WhereWareApplication applicationMock;

    @Mock
    private OrderService orderServiceMock;

    @Mock
    private Observer<List<Order>> orderLiveDataObserver;

    private OrderViewModel orderViewModel;

    @Before
    public void setUp() {
        Mockito.when(applicationMock.getOrderService()).thenReturn(orderServiceMock);
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);

        orderViewModel = new OrderViewModel(applicationMock);

        orderViewModel.getOrderLiveData().observeForever(orderLiveDataObserver);
    }

    @Test
    public void exampleTest_givenOrderServiceWithoutOrder_whenLoadData_thenVerifyOrderLiveDataChanged() {
        List<Order> expectedOrders = Collections.emptyList();
        Mockito.when(applicationMock.getOrderService().getAll()).thenReturn(expectedOrders);

        orderViewModel.loadData();

        Mockito.verify(orderLiveDataObserver).onChanged(expectedOrders);
    }

    @Test
    public void givenOrderServiceWithOneOrder_whenLoadData_thenVerifyOrderLiveDataChanged() {
        List<Order> expectedOrder = new ArrayList<Order>();
        expectedOrder.add(new Order(1, 1,
                Arrays.asList(new Product(1, 1, "Xbox One", "Aisle 3", 1)), 1));

        Mockito.when(applicationMock.getOrderService().getAll()).thenReturn(expectedOrder);

        orderViewModel.loadData();

        Mockito.verify(orderLiveDataObserver).onChanged(expectedOrder);
    }
}