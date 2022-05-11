package at.tugraz.software22;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import at.tugraz.software22.data.DummyOrderRepository;
import at.tugraz.software22.data.OrderRepository;
import at.tugraz.software22.domain.OrderService;
import at.tugraz.software22.ui.MainActivity;
import at.tugraz.software22.ui.OrderActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private OrderService orderService;
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        orderRepository = new DummyOrderRepository();
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void givenOneOrderOnMainActivity_whenOrderIsClicked_thenDisplayOrderActivity() {
        Intents.init();
        Intents.intending(IntentMatchers.hasComponent(OrderActivity.class.getName()));

        String expectedOrder = "Order 1";

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedOrder))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedOrder))
            .perform(ViewActions.click());
    }

    @Test
    public void givenOneOrderOnMainActivity_whenOrderIsClicked_thenSaveStartTime() {
        Intents.init();
        Intents.intending(IntentMatchers.hasComponent(OrderActivity.class.getName()));

        String expectedOrder = "Order 1";

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .perform(ViewActions.click());

        Assert.assertTrue(orderRepository.getAll().get(0).getStartTime()
                .compareTo(LocalDateTime.now()) <= 0);
    }

    @Test
    public void givenOneOrderOnMainActivity_whenOrderIsClicked_thenChangeStatusToInProcess() {
        Intents.init();
        Intents.intending(IntentMatchers.hasComponent(OrderActivity.class.getName()));

        String expectedOrder = "Order 1";

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedOrder))
                .perform(ViewActions.click());

        Assert.assertEquals("IN PROCESS", orderRepository.getAll().get(0).getStatus());
    }
}
