package com.stori.run;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservice.UserService;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.datamodel.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author frey.ren
 * @date 2023/05/26 16:15
 **/
@SpringBootTest(classes = DataApplication.class)
@RunWith(SpringRunner.class)
public class DataApplicationTest {
    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @Resource
    private UserRepository userRepository;

    @Test
    public void addUser() {
//        Long userId = userService.addUser("Tianyi Tan");
//        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(123);
//        Assert.assertEquals(userBalance, 0);
        userRepository.findById(1L);

    }

    @Test
    public void shouldAddUser() {
        Long userId = userService.addUser("Tianyi Tan");
        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(userId);
        Assert.assertEquals(userBalance, 0);
    }
}