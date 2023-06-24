package com.stori.run;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.bankuserservicefacade.UserService;
import com.stori.datamodel.Money;
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
        Money creditLimit = new Money(1000L);
        boolean setCreditLimitRst = creditCardService.setCreditLimit(creditCardId, creditLimit);
        Assert.assertTrue(setCreditLimitRst);
        Money newCreditLimit = creditCardService.getCreditLimit(creditCardId);
        Assert.assertEquals(newCreditLimit, creditLimit);
    }


    @Test
    public void shouldChangeLimitConcurrently() {
        creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
        Money nextCreditLimit = new Money(2000L);
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
        Money curCreditLimit = creditCardService.getCreditLimit(concurrentCreditCardId);
        CreditCardStatusEnum curCreditCardStatus = creditCardService.getStatus(concurrentCreditCardId);
        Assert.assertTrue(curCreditLimit.equals(nextCreditLimit) || curCreditLimit.getNumber() == 0L);
        Assert.assertSame(curCreditCardStatus, CreditCardStatusEnum.BLOCK);
    }

    @Test
    public void shouldChangeCardLimitConcurrently() {
        creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
        Money aCreditLimit = new Money(1000L);
        Money bCreditLimit = new Money(2000L);
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
