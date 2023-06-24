package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreditUsedRecord;
import com.stori.datamodel.repository.CreditUsedRecordRepository;
import com.stori.recordfacade.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "creditUsedRecordService")
public class CreditUsedRecordServiceImpl implements RecordService<CreditUsedRecord> {
    @Resource
    private CreditUsedRecordRepository creditUsedRecordRepository;


    @Override
    public void saveRecord(CreditUsedRecord r) {
        creditUsedRecordRepository.save(r);
    }

    @Override
    public Integer findByRequestId(Long requestId) {
        return creditUsedRecordRepository.findByRequestId(requestId);
    }
}
