package at.tugraz.software22.ui.activity;

import android.content.res.Resources;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.R;
import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.Product;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.service.UserService;

@RunWith(AndroidJUnit4.class)
public class UserActivityTest{
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
    public void givenUserServiceWithOneUser_whenActivityStarted_thenVerifyThatTheUserIsDisplayed() {
        String expectedString = "Name : Stefan Lang";
        List<User> expectedUser = Arrays.asList(new User(1, "Stefan", "Lang"));
        Mockito.when(userServiceMock.getAll()).thenReturn(expectedUser);
        ActivityScenario.launch(UserActivity.class);
        Espresso.onView(ViewMatchers.withText(expectedString))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}