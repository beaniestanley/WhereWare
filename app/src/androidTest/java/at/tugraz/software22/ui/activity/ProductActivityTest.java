package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.Statuses;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.WhereWareApplication;

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
        WhereWareApplication.setOrderService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }

    @Before
    public void setUp() {
        Intents.init();
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void givenOrderWithTwoProducts_whenActivityStarted_thenVerifyThatProductDataIsDisplayed(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(5,productList, 1);

        Mockito.when(orderServiceMock.getOrder(Mockito.anyInt())).thenReturn(order);

        ActivityScenario.launch(ProductActivity.class);

        onView(withText(p1.getName()))
                .check(matches(isDisplayed()));
        onView(withText("Estimated Time: " + p1.getEstimatedTime().toString()))
                .check(matches(isDisplayed()));
        onView(withText("Quantity: " + p1.getProductQuantity()))
                .check(matches(isDisplayed()));
        onView(withText("Location: " + p1.getLocation()))
                .check(matches(isDisplayed()));

        onView(withText(p2.getName()))
                .check(matches(isDisplayed()));
        onView(withText("Estimated Time: " + p2.getEstimatedTime()))
                .check(matches(isDisplayed()));
        onView(withText("Quantity: " + p2.getProductQuantity()))
                .check(matches(isDisplayed()));
        onView(withText("Location: " + p2.getLocation()))
                .check(matches(isDisplayed()));
    }
    @Test
    public void givenOrderWithTwoProducts_whenActivity_thenVerifyThatBarcodeButtonIsShown(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(5,productList, 1);

        Mockito.when(orderServiceMock.getOrder(Mockito.anyInt())).thenReturn(order);

        ActivityScenario.launch(ProductActivity.class);

        onView(withText(resources.getString(R.string.scan_product)))
                .check(matches(isDisplayed()));
    }
    @Test
    public void givenOrderWithTwoProducts_whenClicked_thenVerifyThatCheckBoxIsMarked(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(5,productList, 1);

        Mockito.when(orderServiceMock.getOrder(Mockito.anyInt())).thenReturn(order);

        ActivityScenario.launch(ProductActivity.class);

        onData(anything()).atPosition(0).onChildView(withClassName(Matchers.containsString("CheckBox"))).perform(click());
        onData(anything())
                .atPosition(0)
                .onChildView(withClassName(Matchers.containsString("CheckBox")))
                .check(matches(isChecked()));
    }

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Test
    public void givenOrderWithTwoProducts_whenScan_thenVerifyThatCheckBoxIsMarked(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(5, productList, 1);

        Mockito.when(orderServiceMock.getOrder(1)).thenReturn(order);
        Mockito.when(orderServiceMock.tickProduct(1, 1)).then((Answer<Boolean>) invocation -> {
            Product p = order.getAllProducts_().get(1);
            p.setStatus(p.getStatus() == Statuses.FINISHED ? Statuses.PENDING : Statuses.FINISHED);
            return true;
        });

        Intent startIntent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ProductActivity.class);
        startIntent.putExtra("ORDER_ID", 1);

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ProductActivity.class);
        intent.putExtra("productId", "1");
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        Intents.intending(IntentMatchers.hasComponent(CameraScanActivity.class.getName())).respondWith(result);

        ActivityScenario.launch(startIntent);

        onView(withText(resources.getString(R.string.scan_product))).perform(ViewActions.click());

        onData(anything())
                .atPosition(1)
                .onChildView(withClassName(Matchers.containsString("CheckBox")))
                .check(matches(isChecked()));
    }
    public void givenOrderWithTwoProducts_whenInvalidScan_thenVerifyToastIsDisplayes(){
        Product p1 = new Product(12, 3, "Superproduct", "Storage 5", 1);
        Product p2 = new Product(5, 2, "Topseller", "Storage 2", 2);
        List<Product> productList = new ArrayList<>();
        productList.add(p1);
        productList.add(p2);
        Order order = new Order(5,productList, 1);

        Mockito.when(orderServiceMock.getOrder(Mockito.anyInt())).thenReturn(order);

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), CameraScanActivity.class);
        intent.putExtra("orderId", 7);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        Intents.intending(IntentMatchers.hasComponent(UserViewActivity.class.getName())).respondWith(result);

        ActivityScenario.launch(ProductActivity.class);
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text))
                .check(ViewAssertions.matches(ViewMatchers.withText("Invalid Id")));
    }



}