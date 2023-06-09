package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.datamodel.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {
    private Long creditCarditUserId;
    private Long deleteUserId;
    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    @Test
    public void shouldAddUser() {
        Long userId = userService.addUser("Tianyi Tan");
        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(userId);
        Assert.assertEquals(userBalance, 0);
    }

    @Before
    public void addCreditCardUser() {
        creditCarditUserId = userService.addUser("credit_card_user");
    }

    @Test
    public void shouldAddCreditCard() {
        Long creditCardId = userService.addCreditCard(creditCarditUserId);
        Assert.assertTrue(creditCardId >= 0);
        CreditCardStatus creditCardStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(creditCardStatus, CreditCardStatus.INIT);
        int availableCredit = creditCardService.getCreditLimit(creditCardId);
        Assert.assertEquals(availableCredit, 0);
    }

    @Before
    public void addUserBeforeDelete() {
        deleteUserId = userService.addUser("Frey");
    }

    @Test
    public void shouldDeleteUser() {
        userService.removeUser(deleteUserId);
        int userBalance = userService.getAccountBalance(deleteUserId);
        Assert.assertEquals(userBalance, -1);
    }


}
