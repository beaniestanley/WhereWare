package at.tugraz.software22.ui.activity;



import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.Executor;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class ManagerActivityTest {

    @BeforeClass
    public static void beforeClass() {
        OrderService orderServiceMock = Mockito.mock(OrderService.class);
        WhereWareApplication.setSprintService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    @Test
    public void whenActivityStarted_thenVerifyThatUIIsDisplayed() {
        String expected_title = "Hi Manager, what would you like to do today?";
        String expected_btn1 = "Manage Employees";
        String expected_btn2 = "Manage Orders";
        String expected_btn3 = "Manage Products";
        String expected_btn4 = "Manage Reports";

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(ViewMatchers.withText(expected_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_btn1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_btn2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_btn3))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expected_btn4))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void whenManageOrdersPressed_thenVerifyThatActivityGetsSwitched() {
        Intents.init();
        String expected_btn = "Manage Orders";

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(ViewMatchers.withText(expected_btn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Intents.intended(IntentMatchers.hasComponent(ManagerOrderActivity.class.getName()));
    }

}
