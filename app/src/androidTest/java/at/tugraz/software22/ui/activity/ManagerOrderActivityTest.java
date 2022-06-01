package at.tugraz.software22.ui.activity;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
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

    @BeforeClass
    public static void beforeClass() {
        orderServiceMock = Mockito.mock(OrderService.class);
        WhereWareApplication.setOrderService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    @Test
    public void givenOrderServiceWithFinishedOrders_whenActivityStarted_thenVerifyThatFinishedOrdersGetDisplayed() {
        Product product1 = new Product(12, 1, "Keyboard", "Regal 3E", 1);
        Product product2 = new Product(26, 12, "Controller", "Regal 9D", 2);
        Order order = new Order(45, 23, Arrays.asList(product1, product2), 2);
        order.setStatus(Statuses.FINISHED);
        order.setStartTime(LocalDateTime.MIN);
        order.setEndTime(LocalDateTime.now());
        List<Order> expectedOrders = Collections.singletonList(order);

        String expected_title = "Showing " + 1 + " completed orders for "
                + DateTimeFormatter.ofPattern("MMM YYYY").format(order.getLocalDate());
        String expected_order_title = "Order " + order.getId();
        String expected_collection_time = "Collection Time: " +
            order.getCollectionTime().toHours() + "h " +
            order.getCollectionTime().toMinutes() % 60 + "m";

        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedOrders);

        ActivityScenario.launch(ManagerOrderActivity.class);

        Espresso.onView(ViewMatchers.withText(expected_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_order_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_collection_time))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenOrderServiceWithTwoFinishedOrders_whenMay2022IsSelected_thenVerifyThatOnlyOneFinishedOrderIsDisplayed() {
        Product product1 = new Product(12, 1, "Keyboard", "Regal 3E", 1);
        Product product2 = new Product(26, 12, "Controller", "Regal 9D", 2);
        Order order1 = new Order(45, 23, Arrays.asList(product1, product2), 1);

        Product product3 = new Product(10, 3, "Mouse", "Regal 10A", 3);
        Order order2 = new Order(10, 25, Collections.singletonList(product3), 2)   ;
        order1.setStatus(Statuses.FINISHED);
        order1.setStartTime(LocalDateTime.of(2022, Month.MAY,
                1, 15, 30, 0));
        order1.setEndTime(LocalDateTime.of(2022, Month.MAY,
                1, 16, 15, 0));

        order2.setStatus(Statuses.FINISHED);
        order2.setStartTime(LocalDateTime.of(2022, Month.APRIL,
                29, 19, 30, 0));
        order2.setEndTime(LocalDateTime.of(2022, Month.APRIL,
                29, 20, 0, 0));
        List<Order> expectedOrders = Arrays.asList(order1, order2);

        String expected_title = "Showing " + 1 + " completed orders for "
                + DateTimeFormatter.ofPattern("MMM YYYY").format(order1.getLocalDate());
        String expected_order_title = "Order " + order1.getId();
        String expected_collection_time = "Collection Time: " +
                order1.getCollectionTime().toHours() + "h " +
                order1.getCollectionTime().toMinutes() % 60 + "m";

        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedOrders);

        ActivityScenario.launch(ManagerOrderActivity.class);

        Espresso.onView(ViewMatchers.withText("Filter Orders"))
                .perform(click());

        Espresso.onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(order1.getLocalDate().getYear(),
                        order1.getLocalDate().getMonthValue(),
                        order1.getLocalDate().getDayOfMonth()));

        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(click());

        Espresso.onView(ViewMatchers.withText(expected_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_order_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_collection_time))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenOrderServiceWithTwoFinishedOrders_whenMay2022IsSelected_thenVerifyThatCorrectAverageCollectionTimeIsDisplayed() {
        Product product1 = new Product(12, 1, "Keyboard", "Regal 3E", 1);
        Product product2 = new Product(26, 12, "Controller", "Regal 9D", 2);
        Order order1 = new Order(45, 23, Arrays.asList(product1, product2), 1);

        Product product3 = new Product(10, 3, "Mouse", "Regal 10A", 3);
        Order order2 = new Order(10, 25, Collections.singletonList(product3), 2)   ;
        order1.setStatus(Statuses.FINISHED);
        order1.setStartTime(LocalDateTime.of(2022, Month.MAY,
                1, 15, 30, 0));
        order1.setEndTime(LocalDateTime.of(2022, Month.MAY,
                1, 16, 15, 0));

        order2.setStatus(Statuses.FINISHED);
        order2.setStartTime(LocalDateTime.of(2022, Month.MAY,
                29, 19, 30, 0));
        order2.setEndTime(LocalDateTime.of(2022, Month.MAY,
                29, 20, 0, 0));
        List<Order> expectedOrders = Arrays.asList(order1, order2);

        double average_collection_time = (order1.getCollectionTime().toMinutes()
                + order2.getCollectionTime().toMinutes()) / 2.0f;
        String expected_average_collection_time = "Average collection time: "
                + average_collection_time + " minutes";

        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedOrders);

        ActivityScenario.launch(ManagerOrderActivity.class);

        Espresso.onView(ViewMatchers.withText("Filter Orders"))
                .perform(click());

        Espresso.onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(order1.getLocalDate().getYear(),
                        order1.getLocalDate().getMonthValue(),
                        order1.getLocalDate().getDayOfMonth()));

        Espresso.onView(ViewMatchers.withText("OK"))
                .perform(click());

        Espresso.onView(ViewMatchers.withText(expected_average_collection_time))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
