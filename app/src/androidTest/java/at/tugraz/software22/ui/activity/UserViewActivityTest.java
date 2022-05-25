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
import at.tugraz.software22.domain.User;
import at.tugraz.software22.service.UserService;

@RunWith(AndroidJUnit4.class)
public class UserViewActivityTest{
    private static UserService userServiceMock;
    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        userServiceMock = Mockito.mock(UserService.class);
        WhereWareApplication.setUserService(userServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }
    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void givenEmptyFirstNameAndEmptyLastName_whenCreateButtonPressed_thenDisplayError() {
        String expectedString = "Entered value should not empty";
        ActivityScenario.launch(UserViewActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.input_firstname_field))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
        Espresso.onView(ViewMatchers.withId(R.id.input_lastname_field))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(expectedString)));
    }
    @Test
    public void givenValidUser_whenCreateButtonPressed_thenSetResultAndFinish() {
        String expectedFirstName = "Stefan";
        String expectedLastName = "Schmidt";
        ActivityScenario<UserViewActivity> newActivity = ActivityScenario.launch(UserViewActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.input_firstname_field))
                .perform(ViewActions.typeText(expectedFirstName));
        Espresso.onView(ViewMatchers.withId(R.id.input_lastname_field))
                .perform(ViewActions.typeText(expectedLastName));
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        String actualFirstName = extras.getString(UserViewActivity.INTENT_RESULT_FIRST_NAME);
        String actualLastName = extras.getString(UserViewActivity.INTENT_RESULT_LAST_NAME);
        Assert.assertEquals(expectedFirstName, actualFirstName);
        Assert.assertEquals(expectedLastName, actualLastName);
    }

    @Test
    public void givenUser_whenEditButtonClicked_thenSetResultAndFinish() {
        String expectedFirstName = "Stefan";
        String expectedEditedName = "Hannes";
        String expectedLastName = "Schmidt";
        Integer expectedID = 1;
        User expectedUser = new User(expectedID, expectedFirstName, expectedLastName);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), UserViewActivity.class);
        intent.putExtra(UserActivity.INTENT_EXTRA_USER, expectedUser);
        ActivityScenario newActivity = ActivityScenario.launch(intent);
        Espresso.onView(ViewMatchers.withText(expectedFirstName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedLastName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.edit_user))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.input_firstname_field))
                .perform(ViewActions.replaceText(expectedEditedName));
        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        String actualFirstName = extras.getString(UserViewActivity.INTENT_RESULT_FIRST_NAME);
        String actualLastName = extras.getString(UserViewActivity.INTENT_RESULT_LAST_NAME);
        Integer actualId = extras.getInt(UserViewActivity.INTENT_RESULT_USER_ID);
        Assert.assertEquals(expectedEditedName, actualFirstName);
        Assert.assertEquals(expectedLastName, actualLastName);
        Assert.assertEquals(expectedID, actualId);
    }
    @Test
    public void givenUser_whenDeleteButtonClicked_thenSetResultAndFinish() {
        String expectedFirstName = "Stefan";
        String expectedEditedName = "Hannes";
        String expectedLastName = "Schmidt";
        Integer expectedId = 1;
        User expectedUser = new User(expectedId, expectedFirstName, expectedLastName);
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), UserViewActivity.class);
        intent.putExtra(UserActivity.INTENT_EXTRA_USER, expectedUser);
        ActivityScenario<Activity> newActivity = ActivityScenario.launch(intent);
        Espresso.onView(ViewMatchers.withText(expectedFirstName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(expectedLastName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withText(R.string.edit_user))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.deleteButton))
                .perform(ViewActions.click());
        Instrumentation.ActivityResult result = newActivity.getResult();
        Bundle extras = result.getResultData().getExtras();
        Integer ID = extras.getInt(UserViewActivity.INTENT_RESULT_DELETE_USER);
        Assert.assertEquals(expectedId, ID);
    }
}