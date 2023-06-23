package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.bankuserservicefacade.UserService;
import com.stori.creditfacade.CreditService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.Money;
import com.stori.datamodel.OrderStatusEnum;
import com.stori.datamodel.model.BizOrder;
import com.stori.merchantservicefacade.MerchantService;
import com.stori.orderservicefacade.OrderService;
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
public class BizOrderServiceTest {

    @SofaReference(uniqueId = "creditService")
    CreditService creditService;

    @SofaReference(uniqueId = "userService")
    private UserService userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardService creditCardService;

    @SofaReference(uniqueId = "orderService")
    private OrderService orderService;

    @SofaReference(uniqueId = "merchantService")
    private MerchantService merchantService;

    private static boolean initialized = true;

    private static Long concurrentUserId;

    private static Long userId;

    private static Long creditCardId;

    private final static Money initialCreditLimit = new Money(4000L);

    private final static int creditUsed = 1000;

    private final static int creditReleased = 1500;

    private static Long concurrentCreditCardId;

    private final static int concurrentInitialCreditLimit = 1500;
    private final static int concurrentCreditUsed = 1000;
    private final static int concurrentCreditReleased = 1000;


    private final static String merchantName = "Stori";
    private static Long merchantId;

    private static Long orderId;

    private static final Money orderAmount = new Money(1000L);

    private static long concurrentOrderId;


    private static long requestId = 0L;
    @Before
    public void setup() {
        if (initialized) {
            userId = userService.saveUser("Tianyi Tan");
            creditCardId = userService.saveCreditCard(userId);
            creditCardService.updateCreditCardStatus(creditCardId, CreditCardStatusEnum.ACTIVE);
            creditCardService.setCreditLimit(creditCardId, initialCreditLimit);
            merchantId = merchantService.saveMerchant(merchantName);
            concurrentUserId = userService.saveUser("Frey");
            concurrentCreditCardId = userService.saveCreditCard(concurrentUserId);
            initialized = false;
        }
    }
    @Test
    public void testAshouldCreateOrder() {
        orderId = orderService.createOrder(creditCardId, merchantId, orderAmount, requestId++);
        Assert.assertNotEquals(orderId, null);
        BizOrder bizOrder =  orderService.getOrder(orderId);
        Assert.assertEquals(OrderStatusEnum.ACTIVE, bizOrder.getOrderStatus());
        Assert.assertEquals(merchantName, bizOrder.getMerchant().getName());
    }

    @Test
    public void testBshouldHandleDuplicateRequest() {
        Long duplicateRequestOrderId = orderService.createOrder(creditCardId, merchantId, orderAmount, requestId);
        Assert.assertNotEquals(null, duplicateRequestOrderId);
        Long duplicateRequestOrderId2 = orderService.createOrder(creditCardId, merchantId, orderAmount, requestId);
        Assert.assertNull(duplicateRequestOrderId2);
        boolean cancelRst = orderService.cancelOrder(duplicateRequestOrderId, requestId);
        Assert.assertTrue(cancelRst);
        boolean cancelRst2 = orderService.cancelOrder(duplicateRequestOrderId, requestId);
        Assert.assertNotEquals(true, cancelRst2);
        requestId += 4;
    }
    @Test
    public void testCshouldCancelOrder() {
        boolean cancelRst = orderService.cancelOrder(orderId, requestId++);
        Assert.assertTrue(cancelRst);
        BizOrder bizOrder = orderService.getOrder(orderId);
        Assert.assertSame(OrderStatusEnum.CANCEL, bizOrder.getOrderStatus());
    }


    @Test
    public void testDshouldCancelOrderConcurrently() {
        concurrentOrderId = orderService.createOrder(creditCardId, merchantId, orderAmount, requestId++);
        Thread threadA = new Thread() {
            @Override
            public void run() {
                orderService.cancelOrder(concurrentOrderId, requestId++);
            }
        };
        Thread threadB = new Thread() {
            @Override
            public void run() {
                orderService.cancelOrder(concurrentOrderId, requestId++);
            }
        };
        threadA.start();
        threadB.start();
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        BizOrder order = orderService.getOrder(concurrentOrderId);
        Assert.assertEquals(order.getOrderStatus(), OrderStatusEnum.CANCEL);
    }
}
