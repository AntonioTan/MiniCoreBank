package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.creditfacade.CreditServiceBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CreditServiceApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditServiceTest {
    @SofaReference(uniqueId = "creditService")
    CreditServiceBase creditService;

    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    private static boolean initialized = true;

    private static Long userId;

    private static Long creditCardId;

    private final static int initialCreditLimit = 4000;

    private final static int creditUsed = 1000;

    private final static int creditReleased = 1500;

    @Before
    public void setup() {
        if (initialized) {
            userId = userService.addUser("Tianyi Tan");
            creditCardId = userService.addCreditCard(userId);
            initialized = false;
            creditCardService.setCreditLimit(creditCardId, initialCreditLimit);
        }
    }

    @Test
    public void testAshouldAddUsedCredit() {
        creditService.addCreditUsed(creditCardId, creditUsed);
        int remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit, remainingCredit);
        creditCardService.setCreditCardStatus(creditCardId, CreditCardStatus.ACTIVE);
        creditService.addCreditUsed(creditCardId, creditUsed);
        remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit - creditUsed, remainingCredit);
    }


    @Test
    public void testBshouldReleaseCredit() {
        creditCardService.setCreditCardStatus(creditCardId, CreditCardStatus.BLOCK);
        creditService.releaseCredit(creditCardId, creditReleased);
        int remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit - creditUsed, remainingCredit);
        creditCardService.setCreditCardStatus(creditCardId, CreditCardStatus.ACTIVE);
        creditService.releaseCredit(creditCardId, creditReleased);
        remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit - creditUsed + creditReleased, remainingCredit);
    }
}
