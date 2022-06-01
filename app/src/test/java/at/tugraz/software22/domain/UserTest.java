package at.tugraz.software22.domain;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void givenNewUser_whenConstructor_thenReturnNewUser(){
        Integer expectedId = 1;
        String expectedForename = "Stefan";
        String expectedLastname = "Maier";

        User user = new User(expectedId, expectedForename, expectedLastname);

        Assert.assertEquals(expectedId, user.getId());
        Assert.assertEquals(expectedForename, user.getForename());
        Assert.assertEquals(expectedLastname, user.getLastname());
    }
}