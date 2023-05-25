package com.stori.bankuserservice;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {
    private UserService userService = new UserService();

    @Test
    public void addUser() {
        Long userId = userService.addUser("Tianyi Tan");
        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(userId);
        Assert.assertEquals(userBalance, 0);

    }
}
