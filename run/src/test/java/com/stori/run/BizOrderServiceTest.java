package com.stori.run;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.creditfacade.CreditServiceBase;
import com.stori.datamodel.CreditCardStatus;
import com.stori.datamodel.OrderStatus;
import com.stori.datamodel.model.BizOrder;
import com.stori.merchantservicefacade.MerchantServiceBase;
import com.stori.orderservicefacade.OrderServiceBase;
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
    CreditServiceBase creditService;

    @SofaReference(uniqueId = "userService")
    private UserServiceBase userService;

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    @SofaReference(uniqueId = "orderService")
    private OrderServiceBase orderService;

    @SofaReference(uniqueId = "merchantService")
    private MerchantServiceBase merchantService;

    private static boolean initialized = true;

    private static Long concurrentUserId;

    private static Long userId;

    private static Long creditCardId;

    private final static int initialCreditLimit = 4000;

    private final static int creditUsed = 1000;

    private final static int creditReleased = 1500;

    private static Long concurrentCreditCardId;

    private final static int concurrentInitialCreditLimit = 1500;
    private final static int concurrentCreditUsed = 1000;
    private final static int concurrentCreditReleased = 1000;


    private final static String merchantName = "Stori";
    private static Long merchantId;

    private static Long orderId;

    private static final int orderAmount = 1000;

    private static long concurrentOrderId;


    private static long requestId = 0L;
    @Before
    public void setup() {
        if (initialized) {
            userId = userService.addUser("Tianyi Tan");
            creditCardId = userService.addCreditCard(userId);
            creditCardService.setCreditCardStatus(creditCardId, CreditCardStatus.ACTIVE);
            creditCardService.setCreditLimit(creditCardId, initialCreditLimit);
            merchantId = merchantService.createMerchant(merchantName);
            concurrentUserId = userService.addUser("Frey");
            concurrentCreditCardId = userService.addCreditCard(concurrentUserId);
            initialized = false;
        }
    }
    @Test
    public void testAshouldCreateOrder() {
        orderId = orderService.createOrder(creditCardId, merchantId, orderAmount, requestId++);
        Assert.assertNotEquals(orderId, null);
        BizOrder bizOrder =  orderService.getOrder(orderId);
        Assert.assertEquals(OrderStatus.ACTIVE, bizOrder.getOrderStatus());
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
        Assert.assertSame(OrderStatus.CANCEL, bizOrder.getOrderStatus());
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
        Assert.assertEquals(order.getOrderStatus(), OrderStatus.CANCEL);
    }
}
