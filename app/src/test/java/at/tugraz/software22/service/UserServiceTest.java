package at.tugraz.software22.service;


import net.bytebuddy.asm.Advice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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
        userService.addUser("Stefan", "Humbold");
        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser("Stefan", "Humbold");
    }
    @Test
    public void givenUser_whenRemoveUser_thenVerifyRemoveGetsCalled() {
        User expectedUser = new User(1, "Stefan", "Humbold");
        userService.addUser("Stefan", "Humbold");
        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser("Stefan", "Humbold");
        userService.removeUser(1);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).deleteUser(1);
    }
    @Test
    public void givenUser_whenUpdateUser_thenVerifyRemoveGetsCalled() {
        User expectedUser = new User(1, "Stefan", "Humbold");
        User expectedUpdatedUser = new User(1, "Stefanie", "Humbold");
        userService.addUser("Stefan", "Humbold");
        Mockito.verify(userRepositoryMock, Mockito.times(1)).addUser("Stefan", "Humbold");
        userService.updateUser(1, "Stefanie", "Humbold");
       Mockito.verify(userRepositoryMock, Mockito.times(1)).updateUser(1,"Stefanie", "Humbold");
    }

}