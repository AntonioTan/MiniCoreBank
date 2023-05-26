package com.stori.bankuserservice;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
@ContextConfiguration(classes = { TestConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        Long userId = userService.addUser("Tianyi Tan");
        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(userId);
        Assert.assertEquals(userBalance, 0);

    }
}
