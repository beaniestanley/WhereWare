package at.tugraz.software22;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.ui.activity.ProductActivity;
import at.tugraz.software22.ui.WherewareApplication;

@RunWith(AndroidJUnit4.class)
public class ProductActivityTest {

    private static OrderService orderServiceMock;
    private Resources resources;

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("at.tugraz.software22", appContext.getPackageName());
    }

    //setup test doubles and executor
    @BeforeClass
    public static void beforeClass() {
        orderServiceMock = Mockito.mock(OrderService.class);
        WherewareApplication.setOrderService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WherewareApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenOrderWithTwoProducts_whenActivityStarted_thenVerifyThatProductDataIsDisplayed(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(2,5,productList, 1);

        Mockito.when(orderServiceMock.getOrder(1)).thenReturn(order);

        ActivityScenario.launch(ProductActivity.class);

        Espresso.onView(ViewMatchers.withText(p1.getName()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Estimated Time: " + p1.getEstimatedTime().toString()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Quantity: " + p1.getProductQuantity()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Location: " + p1.getLocation()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(p2.getName()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Estimated Time: " + p2.getEstimatedTime()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Quantity: " + p2.getProductQuantity()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText("Location: " + p2.getLocation()))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}