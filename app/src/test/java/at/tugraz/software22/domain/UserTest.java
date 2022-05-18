package at.tugraz.software22.domain;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void givenNewUser_whenConstructor_thenReturnNewUser(){
        Integer expectedId = 1;
        String expectedForName = "Stefan";
        String expectedLastName = "Maier";

        User user = new User(expectedId, expectedForName, expectedLastName);

        Assert.assertEquals(expectedId, user.getId());
        Assert.assertEquals(expectedForName, user.getForName());
        Assert.assertEquals(expectedLastName, user.getLastName());
    }
}