package com.stori.orderservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.creditfacade.CreditServiceBase;
import com.stori.datamodel.CreditCardStatus;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.Merchant;
import com.stori.datamodel.model.Order;
import com.stori.datamodel.repository.OrderRepository;
import com.stori.merchantservicefacade.MerchantServiceBase;
import com.stori.orderservicefacade.OrderServiceBase;
import com.stori.recordfacade.OrderRecordServiceBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@SofaService(uniqueId = "orderService")
public class OrderService implements OrderServiceBase {
    private final static Logger logger = LogManager.getLogger(OrderService.class);

    @SofaReference(uniqueId="creditCardService")
    private CreditCardServiceBase creditCardService;
    @SofaReference(uniqueId = "creditService")
    private CreditServiceBase creditService;
    @SofaReference(uniqueId = "merchantService")
    private MerchantServiceBase merchantService;

    @SofaReference(uniqueId = "orderRecordService")
    private OrderRecordServiceBase orderRecordService;

    @Resource
    private OrderRepository orderRepository;


    @Override
    public Order getOrder(Long orderId) {
        Optional<Order> optionalOrder =orderRepository.findById(orderId);
        if(optionalOrder.isPresent()) {
            logger.info("Found order with id: " + orderId);
            return optionalOrder.get();
        } else {
            logger.warn("Failed to find order with id: " + orderId);
            return null;
        }

    }

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean createOrder(Long creditCardId, Long merchantId, int amount) {
        Merchant merchant = merchantService.getMerchant(merchantId);
        if(merchant==null) {
            logger.warn("Failed to create order due to invalid merchant id");
            return false;
        }
        boolean addCreditUsedRst = creditService.addCreditUsed(creditCardId, amount);
        if (!addCreditUsedRst) {
            logger.warn("Order creation failed due to credit issue");
            return false;
        }
        Order order = new Order();
        order.setAmount(amount);
        order.setMerchant(merchant);
        CreditCard creditCard = creditCardService.getCreditCard(creditCardId);
        order.setCreditCard(creditCard);
        orderRepository.saveAndFlush(order);
        orderRecordService.addCreateOrderRecord(order.getId());
        return true;
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        Order order = findOrder(orderId);
        if(order==null) {
            logger.warn("Failed to cancel order as order id is invalid");
            return false;
        }
        orderRecordService.addCancelOrderRecord(orderId);
        return true;
    }

    @Override
    public Order findOrder(Long orderId) {
        return null;
    }
}
