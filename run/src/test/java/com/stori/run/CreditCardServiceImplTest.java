package com.stori.run;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.bankuserservicefacade.UserService;
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
public class CreditCardServiceImplTest {
    @SofaReference(uniqueId = "userService")
    private UserService userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardService creditCardService;

    private static boolean initialized = true;

    private static Long userId;

    private static Long concurrentUserId;

    private static Long creditCardId;

    private static Long concurrentCreditCardId;

    @Before
    public void setup() {
        if (initialized) {
            userId = userService.saveUser("Tianyi Tan");
            creditCardId = userService.saveCreditCard(userId);
            initialized = false;
        }
        concurrentUserId = userService.saveUser("Frey");
        concurrentCreditCardId = userService.saveCreditCard(concurrentUserId);
    }


    @Test
    public void testAShouldChangeStatus() {
        CreditCardStatusEnum nextStatus = CreditCardStatusEnum.ACTIVE;
        CreditCardStatusEnum curStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(curStatus, CreditCardStatusEnum.INIT);
        boolean setStatusRst = creditCardService.updateCreditCardStatus(creditCardId, nextStatus);
        Assert.assertTrue(setStatusRst);
        CreditCardStatusEnum newStatus = creditCardService.getStatus(creditCardId);
        Assert.assertEquals(nextStatus, newStatus);
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
    public void shouldChangeLimitConcurrently() {
        creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
        int nextCreditLimit = 2000;
        CreditCardStatusEnum nextCreditCardStatus = CreditCardStatusEnum.BLOCK;
        Thread a = new Thread() {
            @Override
            public void run() {
                creditCardService.setCreditLimit(concurrentCreditCardId, nextCreditLimit);
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                creditCardService.updateCreditCardStatus(concurrentCreditCardId, nextCreditCardStatus);
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
        CreditCardStatusEnum curCreditCardStatus = creditCardService.getStatus(concurrentCreditCardId);
        Assert.assertTrue(curCreditLimit == nextCreditLimit || curCreditLimit == 0);
        Assert.assertSame(curCreditCardStatus, CreditCardStatusEnum.BLOCK);
    }

    @Test
    public void shouldChangeCardLimitConcurrently() {
        creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
        int aCreditLimit = 1000;
        int bCreditLimit = 2000;
        Thread a = new Thread() {
            @Override
            public void run() {
                creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
                creditCardService.setCreditLimit(concurrentCreditCardId, aCreditLimit);
            }
        };
        Thread b = new Thread() {
            @Override
            public void run() {
                creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.BLOCK);
                creditCardService.setCreditLimit(concurrentCreditCardId, bCreditLimit);
            }
        };
        a.start();
        b.start();
//        Assert.assertSame(CreditCardStatus.BLOCK, creditCardService.getStatus(creditCardId));
    }


}
