package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.action.ViewActions.click;

import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.tugraz.software22.R;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class FinishOrderTest{

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void EspressoTest_whenActivityStarted_verifyInputFieldsExist() {
        ActivityScenario.launch(OrderActivity.class);
        Espresso.onData(anything()).inAdapterView(withId(R.id.allOrders)).atPosition(0).perform(click());
        Espresso.onView(withText("FINISH")).perform(click());

        Espresso.onView(withId(R.id.allOrders))
                .check(matches(not(hasDescendant(withText("Order 1")))));
    }
}