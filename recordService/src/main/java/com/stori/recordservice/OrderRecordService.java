package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CancelOrderRecord;
import com.stori.datamodel.model.CreateOrderRecord;
import com.stori.datamodel.model.Order;
import com.stori.datamodel.repository.CancelOrderRecordRepository;
import com.stori.datamodel.repository.CreateOrderRecordRepository;
import com.stori.orderservicefacade.OrderServiceBase;
import com.stori.recordfacade.OrderRecordServiceBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "orderRecordService")
public class OrderRecordService implements OrderRecordServiceBase {
    private static final Logger logger = LogManager.getLogger(OrderRecordService.class);
    @SofaReference(uniqueId = "orderService")
    OrderServiceBase orderService;

    @Resource
    CreateOrderRecordRepository createOrderRecordRepository;

    @Resource
    CancelOrderRecordRepository cancelOrderRecordRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCreateOrderRecord(Long orderId) {
        Order order = orderService.getOrder(orderId);
        if(order==null) {
            logger.warn("Failed to add create order record as order with id: " + orderId + " doesn't exist");
            return ;
        }
        CreateOrderRecord createOrderRecord = new CreateOrderRecord();
        createOrderRecord.setOrder(order);
        createOrderRecordRepository.save(createOrderRecord);
        logger.info("Saved create order record");
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCancelOrderRecord(Long orderId) {
        CancelOrderRecord cancelOrderRecord = new CancelOrderRecord();
        cancelOrderRecord.setOrderId(orderId);
        cancelOrderRecordRepository.save(cancelOrderRecord);
        logger.info("Saved cancel order record");
    }
}
