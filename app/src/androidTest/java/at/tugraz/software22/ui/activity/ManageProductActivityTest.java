package at.tugraz.software22.ui.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class ManageProductActivityTest{
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
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void givenOrderServiceWithOneProduct_whenActivityStarted_thenVerifyThatTheProductIsDisplayed() {
        Product product = new Product(7, 22, "Book", "aisle 3", 7);

        String expectedName = product.getName();
        String expectedLocation = resources.getString(R.string.product_location, product.getLocation());
        String expectedQuantity = resources.getString(R.string.product_quantity, product.getProductQuantity());
        String expectedTime = resources.getString(R.string.product_estimatedtime, product.getEstimatedTime());

        List<Product> expectedProduct = List.of(product);

        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProduct);
        ActivityScenario.launch(ManageProductActivity.class);
        Espresso.onView(ViewMatchers.withText(expectedName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedLocation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedQuantity))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedTime))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void givenActivityResultWithIntent_whenAddProductActivityReturns_thenVerifyThatAddProductMethodOfOrderServiceIsCalled(){
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ManageProductActivity.class);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_PRODUCT_NAME, "Notebook");
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_QUANTITY, 42);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_LOCATION, "aisle 2");
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_ESTIMATED_TIME, 12);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        Intents.intending(IntentMatchers.hasComponent(ManageProductViewActivity.class.getName())).respondWith(result);

        ActivityScenario.launch(ManageProductActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.add_product_button))
                .perform(ViewActions.click());

        Mockito.verify(orderServiceMock, Mockito.times(1)).addProduct("Notebook", 12, "aisle 2", 42);
    }

    @Test
    public void givenActivityResultWithIntent_whenEditingProductActivityReturns_thenVerifyThatEditProductMethodOfOrderServiceIsCalled(){
        List<Product> expectedProduct = List.of(new Product(4, 8, "Phone", "aisle 6", 8));
        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProduct);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ManageProductViewActivity.class);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_PRODUCT_NAME, "Ipad");
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_PRODUCT_ID, 8);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_LOCATION, "aisle 6");
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_QUANTITY, 8);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_ESTIMATED_TIME, 4);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        Intents.intending(IntentMatchers.hasComponent(ManageProductViewActivity.class.getName())).respondWith(result);

        ActivityScenario.launch(ManageProductActivity.class);

        Espresso.onData(CoreMatchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.allProducts)).atPosition(0)
                .perform(ViewActions.click());


        Mockito.verify(orderServiceMock, Mockito.times(1)).updateProduct(8,"Ipad", 4, "aisle 6", 8);
    }
    @Test
    public void givenActivityResultWithIntent_whenDeletingProductActivityReturns_thenVerifyThatDeleteProductMethodOfOrderServiceIsCalled(){
        List<Product> expectedProduct = List.of(new Product(6, 21, "football", "aisle 1", 9));
        Mockito.when(orderServiceMock.getProducts()).thenReturn(expectedProduct);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ManageProductViewActivity.class);
        intent.putExtra(ManageProductViewActivity.INTENT_RESULT_DELETE_PRODUCT, 1);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, intent);
        Intents.intending(IntentMatchers.hasComponent(ManageProductViewActivity.class.getName())).respondWith(result);

        ActivityScenario.launch(ManageProductActivity.class);

        Espresso.onData(CoreMatchers.anything())
                .inAdapterView(ViewMatchers.withId(R.id.allProducts)).atPosition(0)
                .perform(ViewActions.click());


        Mockito.verify(orderServiceMock, Mockito.times(1)).removeProduct(1);
    }

}
