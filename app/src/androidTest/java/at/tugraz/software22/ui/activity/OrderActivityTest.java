package at.tugraz.software22.ui.activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.data.DummyOrderRepository;
import at.tugraz.software22.domain.OrderRepository;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.service.OrderService;

public class OrderActivityTest {
    private OrderService orderService;
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        Intents.init();
        orderRepository = new DummyOrderRepository();
        orderService = new OrderService(orderRepository);
        WhereWareApplication.setOrderService(orderService);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void givenOneOrderOnOrderActivity_whenOrderIsClicked_thenSaveStartTime() {
        Intents.intending(IntentMatchers.hasComponent(OrderActivity.class.getName()));

        String expectedOrder = "Order 1";

        ActivityScenario.launch(OrderActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .perform(ViewActions.click());

        Assert.assertTrue(orderRepository.getOrder(1).getStartTime()
                .compareTo(LocalDateTime.now()) <= 0);
    }

    @Test
    public void givenOneOrderOnOrderActivity_whenOrderIsClicked_thenChangeStatusToInProcess() {
        Intents.intending(IntentMatchers.hasComponent(OrderActivity.class.getName()));

        String expectedOrder = "Order 1";

        ActivityScenario.launch(OrderActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .perform(ViewActions.click());

        Assert.assertEquals(Statuses.IN_PROCESS, orderRepository.getOrder(1).getStatus());
    }
}
