package at.tugraz.software22.ui.activity;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Test;

import at.tugraz.software22.R;

public class MapActivityTest{

    @Test
    public void givenOrderServiceWithFinishedOrders_whenActivityStarted_thenVerifyThatFinishedOrdersGetDisplayed() {

        ActivityScenario.launch(MapActivity.class);

        Espresso.onView(withId(R.id.warehouse))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}