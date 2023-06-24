package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.bankuserservicefacade.UserService;
import com.stori.creditfacade.CreditService;
import com.stori.datamodel.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CreditServiceApplication.class)
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditServiceImplTest {
    @SofaReference(uniqueId = "creditService")
    CreditService creditService;

    @SofaReference(uniqueId = "userService")
    private UserService userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardService creditCardService;

    private static boolean initialized = true;

    private static long requestId = 0L;

    private static Long concurrentUserId;

    private static Long userId;

    private static Long creditCardId;

    private final static Money initialCreditLimit = new Money(4000L);

    private final static Money creditUsed = new Money(1000L);

    private final static Money creditReleased = new Money(1500L);

    private static Long concurrentCreditCardId;


    private final static Money concurrentInitialCreditLimit = new Money(1500L);
    private final static Money concurrentCreditUsed = new Money(1000L);
    private final static Money concurrentCreditReleased = new Money(1000L);


    @Before
    public void setup() {
        if (initialized) {
            userId = userService.saveUser("Tianyi Tan");
            creditCardId = userService.saveCreditCard(userId);
            creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.ACTIVE);
            creditCardService.setCreditLimit(creditCardId, initialCreditLimit);
            concurrentUserId = userService.saveUser("Frey");
            concurrentCreditCardId = userService.saveCreditCard(concurrentUserId);
            initialized = false;
        }
    }

    @Test
    public void testAShouldAddUsedCredit() {
        creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.BLOCK);
        creditService.updateCreditUsed(creditCardId, creditUsed, requestId++);
        Money remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit.getNumber(), remainingCredit.getNumber());
        creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.ACTIVE);
        creditService.updateCreditUsed(creditCardId, creditUsed, requestId++);
        remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit.getNumber() - creditUsed.getNumber(), remainingCredit.getNumber());
    }


    @Test
    public void testBShouldReleaseCredit() {
        creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.BLOCK);
        creditService.updateCreditReleased(creditCardId, creditReleased, requestId++);
        Money remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit.getNumber() - creditUsed.getNumber(), remainingCredit.getNumber());
        creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.ACTIVE);
        creditService.updateCreditReleased(creditCardId, creditReleased, requestId++);
        remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit.getNumber() - creditUsed.getNumber() + creditReleased.getNumber(), remainingCredit.getNumber());
    }

    @Test
    public void testCShouldHandleDuplicateRequest() {
        boolean addRst = creditService.updateCreditUsed(creditCardId, creditUsed, requestId);
        Assert.assertTrue(addRst);
        boolean addRs2 = creditService.updateCreditUsed(creditCardId, creditUsed, requestId);
        Assert.assertFalse(addRs2);
        boolean releaseRst = creditService.updateCreditReleased(creditCardId, creditUsed, requestId);
        Assert.assertTrue(releaseRst);
        boolean releaseRst2 = creditService.updateCreditReleased(creditCardId, creditUsed, requestId);
        Assert.assertFalse(releaseRst2);
        Money remainingCredit = creditService.getRemainingCredit(creditCardId);
        Assert.assertEquals(initialCreditLimit.getNumber() - creditUsed.getNumber() + creditReleased.getNumber(), remainingCredit.getNumber());
    }

    @Test
    public void testDShouldHandleConcurrentRequest() {
        creditCardService.updateCreditCardStatus(concurrentCreditCardId, CreditCardStatusEnum.ACTIVE);
        creditCardService.setCreditLimit(concurrentCreditCardId, concurrentInitialCreditLimit);
        Thread threadA = new Thread() {
            @Override
            public void run() {
                creditService.updateCreditUsed(concurrentCreditCardId, concurrentCreditUsed, requestId++);
            }
        };

        Thread threadB = new Thread() {
            @Override
            public void run() {
                creditService.updateCreditUsed(concurrentCreditCardId, concurrentCreditUsed, requestId++);
            }
        };
        threadA.start();
        threadB.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Money remainingCredit = creditService.getRemainingCredit(concurrentCreditCardId);
        Assert.assertEquals(concurrentInitialCreditLimit.getNumber() - concurrentCreditUsed.getNumber(), remainingCredit.getNumber());
        Thread threadC = new Thread() {
            @Override
            public void run() {
                creditService.updateCreditReleased(concurrentCreditCardId, concurrentCreditReleased, requestId++);
            }
        };
        Thread threadD = new Thread() {
            @Override
            public void run() {
                creditService.updateCreditReleased(concurrentCreditCardId, concurrentCreditReleased, requestId++);
            }
        };
        threadC.start();
        threadD.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        remainingCredit = creditService.getRemainingCredit(concurrentCreditCardId);
        Assert.assertEquals(concurrentInitialCreditLimit.getNumber() - concurrentCreditUsed.getNumber() + concurrentCreditReleased.getNumber()* 2, remainingCredit.getNumber());
    }
}
