package com.stori.run;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.datamodel.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CreditCardServiceApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditCardServiceTest {
    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    private static boolean initialized = true;

    private static Long userId;

    private static Long concurrentUserId;

    private static Long creditCardId;

    private static Long concurrentCreditCardId;

    @Before
    public void setup() {
        if (initialized) {
            userId = userService.addUser("Tianyi Tan");
            creditCardId = userService.addCreditCard(userId);
            initialized = false;
        }
        concurrentUserId = userService.addUser("Frey");
        concurrentCreditCardId = userService.addCreditCard(concurrentUserId);
    }

    @Test
    public void testBShouldSetCreditLimit() {
        int creditLimit = 1000;
        boolean setCreditLimitRst = creditCardService.setCreditLimit(creditCardId, creditLimit);
        Assert.assertTrue(setCreditLimitRst);
        int newCreditLimit = creditCardService.getCreditLimit(creditCardId);
        Assert.assertEquals(newCreditLimit, creditLimit);
    }

    @Test
    public void testAShouldChangeStatus() {
        CreditCardStatus nextStatus = CreditCardStatus.ACTIVE;
        CreditCardStatus curStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(curStatus, CreditCardStatus.INIT);
        boolean setStatusRst = creditCardService.setCreditCardStatus(creditCardId, nextStatus);
        Assert.assertTrue(setStatusRst);
        CreditCardStatus newStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(nextStatus, newStatus);
    }

    @Test
    public void shouldChangeLimitConcurrently() {
        creditCardService.setCreditCardStatus(concurrentCreditCardId, CreditCardStatus.ACTIVE);
        int nextCreditLimit = 2000;
        CreditCardStatus nextCreditCardStatus = CreditCardStatus.BLOCK;
        Thread a = new Thread() {
            @Override
            public void run() {
                creditCardService.setCreditLimit(concurrentCreditCardId, nextCreditLimit);
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                creditCardService.setCreditCardStatus(concurrentCreditCardId, nextCreditCardStatus);
            }
        };
        a.start();
        b.start();
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int curCreditLimit = creditCardService.getCreditLimit(concurrentCreditCardId);
        CreditCardStatus curCreditCardStatus = creditCardService.getStatus(concurrentCreditCardId);
        Assert.assertTrue(curCreditLimit == nextCreditLimit || curCreditLimit == 0);
        Assert.assertSame(curCreditCardStatus, CreditCardStatus.BLOCK);
    }

    @Test
    public void shouldChangeCardLimitConcurrently() {
        creditCardService.setCreditCardStatus(concurrentCreditCardId, CreditCardStatus.ACTIVE);
        int aCreditLimit = 1000;
        int bCreditLimit = 2000;
        Thread a = new Thread() {
            @Override
            public void run() {
                creditCardService.setCreditCardStatus(concurrentCreditCardId, CreditCardStatus.ACTIVE);
                creditCardService.setCreditLimit(concurrentCreditCardId, aCreditLimit);
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                creditCardService.setCreditCardStatus(concurrentCreditCardId, CreditCardStatus.BLOCK);
                creditCardService.setCreditLimit(concurrentCreditCardId, bCreditLimit);
            }
        };
        a.start();
        b.start();
//        Assert.assertSame(CreditCardStatus.BLOCK, creditCardService.getStatus(creditCardId));
    }


}
