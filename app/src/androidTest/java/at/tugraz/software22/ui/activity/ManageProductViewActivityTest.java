package at.tugraz.software22.ui.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class ManageProductViewActivityTest {
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
    public void givenEmptyProductData_whenCreateButtonPressed_thenDisplayError() {
        String expectedString = "Entered value should not empty";
        ActivityScenario.launch(ManageProductViewActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.button_create_product))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.input_product_name_field))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
        Espresso.onView(ViewMatchers.withId(R.id.input_product_time))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
        Espresso.onView(ViewMatchers.withId(R.id.input_location_field))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
        Espresso.onView(ViewMatchers.withId(R.id.input_quantity_field))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
    }

    @Test
    public void givenValidProduct_whenCreateButtonPressed_thenSetResultAndFinish() {
        String expectedName = "Pool";
        String expectedQuantity = "1";
        String expectedTime = "4";
        String expectedLocation = "aisle 1";

        ActivityScenario<ManageProductViewActivity> newActivity = ActivityScenario.launch(ManageProductViewActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.input_product_name_field))
                .perform(ViewActions.typeText(expectedName));
        Espresso.onView(ViewMatchers.withId(R.id.input_product_time))
                .perform(ViewActions.typeText(expectedTime));
        Espresso.onView(ViewMatchers.withId(R.id.input_location_field))
                .perform(ViewActions.typeText(expectedLocation));
        Espresso.onView(ViewMatchers.withId(R.id.input_quantity_field))
                .perform(ViewActions.typeText(expectedQuantity));

        Espresso.onView(ViewMatchers.withId(R.id.button_create_product))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        String actualName = extras.getString(ManageProductViewActivity.INTENT_RESULT_PRODUCT_NAME);
        String actualTime = extras.getString(ManageProductViewActivity.INTENT_RESULT_ESTIMATED_TIME);
        String actualQuantity = extras.getString(ManageProductViewActivity.INTENT_RESULT_QUANTITY);
        String actualLocation = extras.getString(ManageProductViewActivity.INTENT_RESULT_LOCATION);
        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedLocation, actualLocation);
        Assert.assertEquals(expectedTime, actualTime);
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void givenOneProduct_whenEditButtonClicked_thenSetResultAndFinish() {
        String expectedName = "Pool";
        String expectedQuantity = "1";
        String expectedTime = "4";
        String expectedLocation = "aisle 1";
        String expectedEditedQuantity = "4";
        Integer expectedID = 1;

        Product expectedProduct = new Product(Integer.valueOf(expectedTime), Integer.valueOf(expectedQuantity), expectedName, expectedLocation, expectedID);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ManageProductViewActivity.class);
        intent.putExtra(ManageProductActivity.INTENT_EXTRA_PRODUCT, expectedProduct);
        ActivityScenario newActivity = ActivityScenario.launch(intent);
        Espresso.onView(ViewMatchers.withText(expectedName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedLocation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedQuantity))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedTime))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.edit_product))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.input_quantity_field))
                .perform(ViewActions.replaceText(expectedEditedQuantity));
        Espresso.onView(ViewMatchers.withId(R.id.button_create_product))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        String actualName = extras.getString(ManageProductViewActivity.INTENT_RESULT_PRODUCT_NAME);
        String actualTime = extras.getString(ManageProductViewActivity.INTENT_RESULT_ESTIMATED_TIME);
        String actualQuantity = extras.getString(ManageProductViewActivity.INTENT_RESULT_QUANTITY);
        String actualLocation = extras.getString(ManageProductViewActivity.INTENT_RESULT_LOCATION);
        Integer actualId = extras.getInt(ManageProductViewActivity.INTENT_RESULT_PRODUCT_ID);

        Assert.assertEquals(expectedEditedQuantity, actualQuantity);
        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedTime, actualTime);
        Assert.assertEquals(expectedLocation, actualLocation);
        Assert.assertEquals(expectedID, actualId);
    }

    @Test
    public void givenProduct_whenDeleteButtonClicked_thenSetResultAndFinish() {
        String expectedName = "Pool";
        String expectedQuantity = "1";
        String expectedTime = "4";
        String expectedLocation = "aisle 1";
        Integer expectedId = 1;

        Product expectedProduct = new Product(Integer.valueOf(expectedTime), Integer.valueOf(expectedQuantity), expectedName, expectedLocation, expectedId);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), ManageProductViewActivity.class);
        intent.putExtra(ManageProductActivity.INTENT_EXTRA_PRODUCT, expectedProduct);
        ActivityScenario<Activity> newActivity = ActivityScenario.launch(intent);
        Espresso.onView(ViewMatchers.withText(expectedName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedLocation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedQuantity))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedTime))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.edit_product))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.deleteButton))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        Integer ID = extras.getInt(ManageProductViewActivity.INTENT_RESULT_DELETE_PRODUCT);
        Assert.assertEquals(expectedId, ID);
    }

}
