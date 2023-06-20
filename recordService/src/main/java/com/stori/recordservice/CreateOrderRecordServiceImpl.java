package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreateOrderRecord;
import com.stori.datamodel.repository.CreateOrderRecordRepository;
import com.stori.recordfacade.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "createOrderRecordService")
public class CreateOrderRecordServiceImpl implements RecordService<CreateOrderRecord> {
    @Resource
    CreateOrderRecordRepository createOrderRecordRepository;
    @Override
    public void saveRecord(CreateOrderRecord r) {
        createOrderRecordRepository.save(r);
    }

    @Override
    public Integer findByRequestId(Long requestId) {
        return createOrderRecordRepository.findByRequestId(requestId);
    }
}
