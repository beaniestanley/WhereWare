package at.tugraz.software22.ui.activity;



import static android.app.PendingIntent.getActivity;
import static android.service.autofill.Validators.not;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static java.util.regex.Pattern.matches;

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

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.File;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.service.OrderService;

@RunWith(AndroidJUnit4.class)
public class ManagerActivityTest {
    private Resources resources;

    @BeforeClass
    public static void beforeClass() {
        OrderService orderServiceMock = Mockito.mock(OrderService.class);
        WhereWareApplication.setOrderService(orderServiceMock);

        Executor currentThreadExecutor = Runnable::run;
        WhereWareApplication.setBackgroundExecutor(currentThreadExecutor);
    }
    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
    }

    @Test
    public void whenActivityStarted_thenVerifyThatUIIsDisplayed() {
        String expected_title = "Hi Manager, what would you like to do today?";
        String expected_btn1 = "Manage Employees";
        String expected_btn2 = "Manage Orders";
        String expected_btn3 = "Manage Products";
        String expected_btn4 = "Generate Report";

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(withText(expected_title))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(withText(expected_btn1))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(withText(expected_btn2))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(withText(expected_btn3))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(withText(expected_btn4))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void whenManageOrdersPressed_thenVerifyThatActivityGetsSwitched() {
        Intents.init();
        String expected_btn = "Manage Orders";

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(withText(expected_btn))
                .check(ViewAssertions.matches(isDisplayed()))
                .perform(ViewActions.click());

        Intents.intended(IntentMatchers.hasComponent(ManagerOrderActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void whenActivityStarted_thenVerifyThatReportUIIsDisplayed() {
        String expected_picker_from_hint = "Starting Date";
        String expected_picker_to_hint = "End Date";
        int expected_picker_from_id = R.id.start_date_picker;
        int expected_picker_to_id = R.id.end_date_picker;

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(withText(expected_picker_from_hint))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(withText(expected_picker_to_hint))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(ViewMatchers.withId(expected_picker_from_id))
                .check(ViewAssertions.matches(isDisplayed()));

        Espresso.onView(ViewMatchers.withId(expected_picker_to_id))
                .check(ViewAssertions.matches(isDisplayed()));
    }


    @Test
    public void whenReportGenerationStarted_thenVerifyThatReportIsCreated() {
        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.start_date_picker)).perform(ViewActions.replaceText("01.01.1990"));
        Espresso.onView(ViewMatchers.withId(R.id.end_date_picker)).perform(ViewActions.replaceText("01.01.2050"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonManageReports)).perform(ViewActions.click());

        File file = new File("storage/self/primary/Documents/Report.pdf");
        assertTrue(file.exists());
    }

    @Test
    public void whenInvalidDate_ToastIsDisplayed() {

        ActivityScenario.launch(ManagerActivity.class);

        Espresso.onView(ViewMatchers.withId(R.id.start_date_picker))
                .perform(ViewActions.replaceText("11.50.1998"));
        Espresso.onView(ViewMatchers.withId(R.id.end_date_picker))
                .perform(ViewActions.replaceText("11.10.1998"));
        Espresso.onView(ViewMatchers.withId(R.id.buttonManageReports))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.start_date_picker)).check(ViewAssertions.matches(hasErrorText(resources.getString(R.string.invalid_date))));
    }
}
