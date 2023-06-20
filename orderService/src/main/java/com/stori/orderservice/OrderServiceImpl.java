package com.stori.orderservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.creditfacade.CreditService;
import com.stori.datamodel.OrderStatusEnum;
import com.stori.datamodel.model.*;
import com.stori.datamodel.repository.OrderRepository;
import com.stori.merchantservicefacade.MerchantService;
import com.stori.orderservicefacade.OrderService;
import com.stori.recordfacade.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@SofaService(uniqueId = "orderService")
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @SofaReference(uniqueId="creditCardService")
    private CreditCardService creditCardService;
    @SofaReference(uniqueId = "creditService")
    private CreditService creditService;
    @SofaReference(uniqueId = "merchantService")
    private MerchantService merchantService;

    @SofaReference(uniqueId = "createOrderRecordService")
    RecordService<CreateOrderRecord> createOrderRecordRecordService;

    @SofaReference(uniqueId = "cancelOrderRecordService")
    RecordService<CancelOrderRecord> cancelOrderRecordRecordService;


    @Resource
    private OrderRepository orderRepository;

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Long createOrder(Long creditCardId, Long merchantId, Integer amount, Long requestId) {
        int foundRequest = createOrderRecordRecordService.findByRequestId(requestId);
        if(foundRequest!=0) {
            logger.warn("Failed to create order due to duplicate request");
            return null;
        }
        Merchant merchant = merchantService.getMerchant(merchantId);
        if(merchant==null) {
            logger.warn("Failed to create order due to invalid merchant id");
            return null;
        }
        boolean addCreditUsedRst = creditService.updateCreditUsed(creditCardId, amount, requestId);
        if (!addCreditUsedRst) {
            logger.warn("Order creation failed due to credit issue");
            return null;
        }
        BizOrder bizOrder = new BizOrder();
        bizOrder.setAmount(amount);
        bizOrder.setMerchant(merchant);
        CreditCard creditCard = creditCardService.getCreditCard(creditCardId);
        bizOrder.setCreditCard(creditCard);
        CreateOrderRecord createOrderRecord = new CreateOrderRecord(requestId);
        createOrderRecord.setOrder(bizOrder);
        createOrderRecordRecordService.saveRecord(createOrderRecord);
        orderRepository.saveAndFlush(bizOrder);
        return bizOrder.getId();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Boolean cancelOrder(Long orderId, Long requestId) {
        int foundRequest = cancelOrderRecordRecordService.findByRequestId(requestId);
        if(foundRequest!=0) {
            logger.warn("Failed to create order due to duplicate request");
            return false;
        }
        BizOrder bizOrder = getOrder(orderId);
        if(bizOrder ==null) {
            logger.warn("Failed to cancel order as order id is invalid");
            return false;
        }
        boolean addCreditReleasedRst = creditService.updateCreditReleased(bizOrder.getCreditCard().getId(), bizOrder.getAmount(), requestId);
        if (!addCreditReleasedRst) {
            logger.warn("Order cancellation failed due to credit issue");
            return false;
        }
        bizOrder.setOrderStatus(OrderStatusEnum.CANCEL);
        CancelOrderRecord cancelOrderRecord = new CancelOrderRecord(requestId);
        cancelOrderRecord.setOrderId(orderId);
        cancelOrderRecordRecordService.saveRecord(cancelOrderRecord);
        orderRepository.saveAndFlush(bizOrder);
        return true;
    }

    @Override
    public BizOrder getOrder(Long orderId) {
        Optional<BizOrder> optionalOrder = orderRepository.findById(orderId);
        if(optionalOrder.isPresent()) {
            if(logger.isDebugEnabled()) logger.info("Found the order with id: " + orderId);
            return optionalOrder.get();
        } else {
            logger.warn("Failed to find order with id: " + orderId);
            return null;
        }
    }
}
