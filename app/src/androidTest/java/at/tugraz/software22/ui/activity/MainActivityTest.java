package at.tugraz.software22.ui.activity;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{
    private static OrderService orderServiceMock;
    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        orderServiceMock = Mockito.mock(OrderService.class);
        WhereWareApplication.setOrderService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }
    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void exampleEspressoTest_whenActivityStarted_thenVerifyThatAppNameIsDisplayed() {
        String expectedTitle = resources.getString(R.string.app_name);

        // Launch activity after setup (in that case no setup required)
        ActivityScenario.launch(OrderActivity.class);

        Espresso.onView(ViewMatchers.withText(expectedTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenSprintServiceWithTwoSprints_whenActivityStarted_thenVerifyThatBothSprintTitlesAndStoryPointsAreDisplayed() {
        String expectedTitleOrder1 = "Order 1";
        String expectedTitleOrder2 = "Order 2";
        String expectedTimeOrder1 = "Estimated time: 0h 1m";
        String expectedTimeOrder2 = "Estimated time: 0h 2m";
        String expectedQuantityOrder1 = "Quantity: 1";
        String expectedQuantityOrder2 = "Quantity: 2";
        Integer expectedQuantity = 1;
        Integer expectedID = 1;
        Integer expectedTime = 1;
        String expectedName = "Xbox One";
        String expectedLocation = "Aisle 3";
        List<Order> expectedOrders = Arrays.asList(new Order(expectedQuantity,
                        Collections.singletonList(new Product(expectedTime, expectedQuantity, expectedName, expectedLocation, expectedID)), 1),
                new Order( 2,
                        Collections.singletonList(new Product(2, expectedQuantity, expectedName, expectedLocation, expectedID)), 2));
        Mockito.when(orderServiceMock.getAll()).thenReturn(expectedOrders);
        ActivityScenario.launch(OrderActivity.class);
        Espresso.onView(ViewMatchers.withText(expectedTitleOrder1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedTitleOrder2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedQuantityOrder1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedQuantityOrder2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(expectedTimeOrder1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedTimeOrder2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenNothing_whenActivityStarted_thenVerifyThatLanguageButtonsAreDisplayed() {
        String btnGerman = "German";
        String btnEnglish = "English";

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withText(btnEnglish))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText(btnGerman))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void givenNothing_whenActivityStarted_thenVerifyThatLanguageButtonWorks() {
        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(Matchers.allOf(ViewMatchers.isClickable(), ViewMatchers.withText("Login")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText("English"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("German"))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Anmelden"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}