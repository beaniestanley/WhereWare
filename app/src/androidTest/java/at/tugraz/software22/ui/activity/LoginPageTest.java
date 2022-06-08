package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.res.Resources;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.R;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


@RunWith(AndroidJUnit4.class)
public class LoginPageTest{


    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void EspressoTest_whenActivityStarted_verifyInputFieldsExist() {

        ActivityScenario.launch(MainActivity.class);
        Espresso.onView(ViewMatchers.withId(R.id.login_button))
                .check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.login_name))
                .check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.login_passwd))
                .check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void EspressoTest_whenRightLoginData_thenManagerActivityIsStarted() throws InterruptedException {
        Intents.init();

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.login_name))
                .perform(click(), ViewActions.replaceText("admin"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.login_passwd))
                .perform(click(), ViewActions.replaceText("admin"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withText("LOGIN")).perform(click());

        intended(hasComponent(ManagerActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void EspressoTest_whenRightLoginData_thenOrderActivityIsStarted() throws InterruptedException {
        Intents.init();

        ActivityScenario.launch(MainActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.login_name))
                .perform(click(), ViewActions.replaceText("employee"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.login_passwd))
                .perform(click(), ViewActions.replaceText("employee"), closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withText("LOGIN"))
                .perform(click());

        intended(hasComponent(OrderActivity.class.getName()));

        Intents.release();
    }
}