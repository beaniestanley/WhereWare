package at.tugraz.software22.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import at.tugraz.software22.domain.User;

public class UserRepositoryTest {

    @Test
    public void  givenEmptyRepository_whenGetAll_thenReturnEmptyList(){

        DummyUserRepository dummyUserRepository = new DummyUserRepository();

        List<User> usersActual = dummyUserRepository.getAll();

        int expectedSize = 0;
        Assert.assertEquals(expectedSize, usersActual.size());
    }

    @Test
    public void givenEmptyRepository_whenAddUser_thenReturnOneUser(){
        String expectedForename = "Stefan";
        String expectedLastname = "Maier";

        DummyUserRepository dummyUserRepository = new DummyUserRepository();

        dummyUserRepository.addUser(expectedForename, expectedLastname);

        List<User> usersActual = dummyUserRepository.getAll();

        int expectedSize = 1;
        Assert.assertEquals(expectedSize, usersActual.size());
    }
    @Test
    public void givenRepositoryWithOneUser_whenDeleteUser_thenReturnEmptyList(){

        Integer expectedId = 1;
        String expectedForename = "Stefan";
        String expectedLastname = "Maier";

        DummyUserRepository dummyUserRepository = new DummyUserRepository();

        dummyUserRepository.addUser(expectedForename, expectedLastname);

        dummyUserRepository.deleteUser(expectedId);

        List<User> usersActual = dummyUserRepository.getAll();

        int expectedSize = 0;
        Assert.assertEquals(expectedSize, usersActual.size());
    }

    @Test
    public void givenRepositoryWithOneUser_whenUpdated_thenReturnUpdatedList(){

        Integer expectedId = 1;
        String expectedForename = "Stefan";
        String expectedLastname = "Maier";
        String expectedUpdatedForename = "Stefanie";
        String expectedUpdatedLastname = "Humbold";

        User user = new User(expectedId, expectedForename, expectedLastname);
        User updatedUser = new User(expectedId, expectedUpdatedForename, expectedUpdatedLastname);

        DummyUserRepository dummyUserRepository = new DummyUserRepository();

        dummyUserRepository.addUser(expectedForename, expectedLastname);

        dummyUserRepository.updateUser(expectedId, "Stefanie", "Humbold");

        List<User> usersActual = dummyUserRepository.getAll();

        int expectedSize = 1;
        Assert.assertEquals(expectedSize, usersActual.size());
        Assert.assertEquals(expectedUpdatedForename, usersActual.get(0).getForename());
        Assert.assertEquals(expectedUpdatedLastname, usersActual.get(0).getLastname());
    }
}