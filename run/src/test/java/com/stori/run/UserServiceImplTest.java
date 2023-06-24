package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.bankuserservicefacade.UserService;
import com.stori.datamodel.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    private Long creditCarditUserId;
    private Long deleteUserId;
    @SofaReference(uniqueId = "userService")
    private UserService userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardService creditCardService;

    @Test
    public void shouldAddUser() {
        Long userId = userService.saveUser("Tianyi Tan");
        Assert.assertTrue(userId >= 0);
        int userBalance = userService.getAccountBalance(userId);
        Assert.assertEquals(userBalance, 0);
    }

    @Before
    public void addCreditCardUser() {
        creditCarditUserId = userService.saveUser("credit_card_user");
    }

    @Test
    public void shouldAddCreditCard() {
        Long creditCardId = userService.saveCreditCard(creditCarditUserId);
        Assert.assertTrue(creditCardId >= 0);
        CreditCardStatusEnum creditCardStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(creditCardStatus, CreditCardStatusEnum.INIT);
        Money availableCredit = creditCardService.getCreditLimit(creditCardId);
        Assert.assertEquals(availableCredit, 0);
    }

    @Before
    public void addUserBeforeDelete() {
        deleteUserId = userService.saveUser("Frey");
    }

    @Test
    public void shouldDeleteUser() {
        userService.removeUser(deleteUserId);
        int userBalance = userService.getAccountBalance(deleteUserId);
        Assert.assertEquals(userBalance, -1);
    }


}
