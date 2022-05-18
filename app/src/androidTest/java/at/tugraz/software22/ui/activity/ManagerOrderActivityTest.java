package at.tugraz.software22.ui.activity;


import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class ManagerOrderActivityTest {
    private static OrderService orderServiceMock;
    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        orderServiceMock = Mockito.mock(OrderService.class);
        WhereWareApplication.setSprintService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenOrderServiceWithFinishedOrders_whenActivityStarted_thenVerifyThatFinishedOrdersGetDisplayed() {
        Product product1 = new Product(12, 1, "Keyboard", "Regal 3E", 1);
        Product product2 = new Product(26, 12, "Controller", "Regal 9D", 2);
        Order order = new Order(45, 23, Arrays.asList(product1, product2), 2);
        order.setStatus(Statuses.FINISHED);
        order.setStartTime(LocalDateTime.MIN);
        order.setEndTime(LocalDateTime.now());
        List<Order> expectedOrders = Arrays.asList(order);

        String expected_title = "Completed Orders";
        String expected_order_title = "Order " + order.getId();
        String expected_collection_time = "Collection Time: " +
            order.getCollectionTime().toHours() + "h " +
            order.getCollectionTime().toMinutes() / 60 + "m";

        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedOrders);

        ActivityScenario.launch(ManagerOrderActivity.class);

        Espresso.onView(ViewMatchers.withText(expected_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_order_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_collection_time))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
