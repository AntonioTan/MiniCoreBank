package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CancelOrderRecord;
import com.stori.datamodel.model.CreateOrderRecord;
import com.stori.datamodel.repository.CancelOrderRecordRepository;
import com.stori.datamodel.repository.CreateOrderRecordRepository;
import com.stori.recordfacade.OrderRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "orderRecordService")
public class OrderRecordServiceImpl extends RecordServiceImpl implements OrderRecordService {
    private static final Logger logger = LoggerFactory.getLogger(OrderRecordServiceImpl.class);

    @Resource
    CreateOrderRecordRepository createOrderRecordRepository;

    @Resource
    CancelOrderRecordRepository cancelOrderRecordRepository;


    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCreateOrderRecord(CreateOrderRecord createOrderRecord) {
        createOrderRecordRepository.save(createOrderRecord);
        if(logger.isDebugEnabled()) logger.info("Saved create order record");
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCancelOrderRecord(CancelOrderRecord cancelOrderRecord) {
        cancelOrderRecordRepository.save(cancelOrderRecord);
        if(logger.isDebugEnabled()) logger.info("Saved cancel order record");
    }
}
