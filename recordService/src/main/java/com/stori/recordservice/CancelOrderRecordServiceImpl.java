package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CancelOrderRecord;
import com.stori.datamodel.repository.CancelOrderRecordRepository;
import com.stori.recordfacade.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "cancelOrderRecordService")
public class CancelOrderRecordServiceImpl implements RecordService<CancelOrderRecord> {
    @Resource
    CancelOrderRecordRepository cancelOrderRecordRepository;
    @Override
    public void saveRecord(CancelOrderRecord r) {
        cancelOrderRecordRepository.save(r);
    }

    @Override
    public Integer findByRequestId(Long requestId) {
        return cancelOrderRecordRepository.findByRequestId(requestId);
    }
}
