package at.tugraz.software22.ui.viewmodel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import at.tugraz.software22.WhereWareApplication;
import at.tugraz.software22.domain.Order;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.service.OrderService;
import at.tugraz.software22.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserViewModelTest{
    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final Executor currentThreadExecutor = Runnable::run;

    @Mock
    private WhereWareApplication applicationMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private Observer<List<User>> userLiveDataObserver;

    private UserViewModel userViewModel;

    @Before
    public void setUp() {
        Mockito.when(applicationMock.getUserService()).thenReturn(userServiceMock);
        Mockito.when(applicationMock.getBackgroundExecutor()).thenReturn(currentThreadExecutor);

        userViewModel = new UserViewModel(applicationMock);

        userViewModel.getUserLiveData().observeForever(userLiveDataObserver);
    }

    @Test
    public void exampleTest_givenUserServiceWithoutUser_whenLoadData_thenVerifyUserLiveDataChanged() {
        List<User> expectedUsers = Collections.emptyList();
        Mockito.when(applicationMock.getUserService().getAll()).thenReturn(expectedUsers);

        userViewModel.loadData();

        Mockito.verify(userLiveDataObserver).onChanged(expectedUsers);
    }
    @Test
    public void givenUserServiceWithOneUser_whenLoadData_thenVerifyUserLiveDataChanged() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, "Stefan", "Hiebler"));
        Mockito.when(applicationMock.getUserService().getAll()).thenReturn(expectedUsers);

        userViewModel.loadData();

        Mockito.verify(userLiveDataObserver).onChanged(expectedUsers);
    }
    @Test
    public void givenUserServiceWithOneUser_whenDeletedData_thenVerifyUserLiveDataChanged() {
        Mockito.when(applicationMock.getUserService().getAll()).thenReturn(Collections.emptyList());

        userViewModel.loadData();
        userViewModel.removeUser(1);
        Mockito.verify(userLiveDataObserver, Mockito.times(2)).onChanged(Collections.emptyList());
    }
    @Test
    public void givenUserServiceWithOneUser_whenUpdatedData_thenVerifyUserLiveDataChanged() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1, "Hannes", "Peter"));
        Mockito.when(applicationMock.getUserService().getAll()).thenReturn(expectedUsers);

        userViewModel.loadData();
        userViewModel.updateUser(1,"Hannes", "Peter");
        Mockito.verify(userLiveDataObserver, Mockito.times(2)).onChanged(expectedUsers);
    }
}