package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservice.UserService;
import com.stori.bankuserservicefacade.CreditCardBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.datamodel.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {
    Long creditCarditUserId;
    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardBase creditCardService;

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
        CreditCardStatus creditCardStatus = creditCardService.getStatus(creditCardId);
        Assert.assertTrue(creditCardId >= 0);
        Assert.assertEquals(creditCardStatus, CreditCardStatus.INIT);
    }
}
