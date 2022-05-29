package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.content.res.Resources;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.service.OrderService;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import java.util.concurrent.Executor;

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
        Espresso.onView(ViewMatchers.withId(R.id.finish_button_1)).perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.finish_button_1)).check(matches(withText("FINISHED")));
    }
}