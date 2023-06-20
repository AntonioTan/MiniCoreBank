package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.repository.CreditReleaseRecordRepository;
import com.stori.recordfacade.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId="creditReleasedRecordService")
public class CreditReleasedRecordServiceImpl implements RecordService<CreditReleasedRecord> {
    @Resource
    CreditReleaseRecordRepository creditReleaseRecordRepository;

    @Override
    public void saveRecord(CreditReleasedRecord r) {
        creditReleaseRecordRepository.save(r);
    }

    @Override
    public Integer findByRequestId(Long requestId) {
        return creditReleaseRecordRepository.findByRequestId(requestId);
    }
}
