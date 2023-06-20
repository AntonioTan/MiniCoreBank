package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;
import com.stori.datamodel.repository.CreditReleaseRecordRepository;
import com.stori.datamodel.repository.CreditUsedRecordRepository;
import com.stori.recordfacade.CreditRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@SofaService(uniqueId = "creditRecordService")
public class CreditRecordServiceImpl implements CreditRecordService {
    private final static Logger logger = LoggerFactory.getLogger(CreditRecordServiceImpl.class);
    @Resource
    private CreditUsedRecordRepository creditUsedRecordRepository;

    @Resource
    private CreditReleaseRecordRepository creditReleaseRecordRepository;


    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public void addCreditUsedRecord(CreditUsedRecord creditUsedRecord) {
        creditUsedRecordRepository.save(creditUsedRecord);
        if(logger.isDebugEnabled()) logger.info("Add credit used record");
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCreditReleaseRecord(CreditReleasedRecord creditReleasedRecord) {
        creditReleaseRecordRepository.save(creditReleasedRecord);
        if(logger.isDebugEnabled()) logger.info("Add credit released record");
    }
}
