package at.tugraz.software22.service;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Collections;
import java.util.List;
import at.tugraz.software22.domain.User;
import at.tugraz.software22.domain.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest{
    @Mock
    private UserRepository userRepositoryMock;

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepositoryMock);
    }
    Integer expectedId = 1;
    String expectedForename = "Stefan";
    String expectedLastname = "Maier";

    User user = new User(expectedId, expectedForename, expectedLastname);

    @Test
    public void givenEmptyRepository_whenGetAll_thenReturnNoOrders() {
        List<User> expectedUsers = Collections.emptyList();
        Mockito.when(userRepositoryMock.getAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAll();
        Assert.assertEquals(0, actualUsers.size());
    }

    @Test
    public void givenUser_whenAddUser_thenVerifyAddGetsCalled() {
        User expectedUser = new User(1, "Stefan", "Humbold");
        userService.addUser(expectedUser);

        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser(expectedUser);
    }
    @Test
    public void givenUser_whenRemoveUser_thenVerifyRemoveetsCalled() {
        User expectedUser = new User(1, "Stefan", "Humbold");
        userService.addUser(expectedUser);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser(expectedUser);
        userService.removeUser(1);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).deleteUser(1);
    }
    @Test
    public void givenUser_whenUpdateUser_thenVerifyRemoveetsCalled() {
        User expectedUser = new User(1, "Stefan", "Humbold");
        User expectedUpdatedUser = new User(1, "Stefan", "Humbold");
        userService.addUser(expectedUser);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser(expectedUser);
        userService.updateUser(1, expectedUpdatedUser);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).updateUser(1, expectedUpdatedUser);
    }

}