package com.stori.run;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CreditCardServiceApplication.class)
@RunWith(SpringRunner.class)
public class CreditCardServiceTest {
    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    private boolean initialized = true;

    private Long userId;

    private Long creditCardId;

    @Before
    public void setup() {
        if(initialized) {
            userId = userService.addUser("Tianyi Tan");
            creditCardId = userService.addCreditCard(userId);
            initialized = false;
        }
    }

    @Test
    public void shouldSetCreditLimit() {
        int creditLimit = 1000;
        boolean setCreditLimitRst = creditCardService.setCreditLimit(creditCardId, creditLimit);
        Assert.assertTrue(setCreditLimitRst);
        int newCreditLimit = creditCardService.getCreditLimit(creditCardId);
        Assert.assertEquals(newCreditLimit, creditLimit);
    }

    @Test
    public void shouldChangeStatus() {
        CreditCardStatus nextStatus = CreditCardStatus.ACTIVE;
        CreditCardStatus curStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(curStatus, CreditCardStatus.INIT);
        boolean setStatusRst = creditCardService.setCreditCardStatus(creditCardId, nextStatus);
        Assert.assertTrue(setStatusRst);
        CreditCardStatus newStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(nextStatus, newStatus);
    }


}
