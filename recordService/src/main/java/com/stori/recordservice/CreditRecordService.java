package com.stori.recordservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;
import com.stori.datamodel.repository.CreditCardRepository;
import com.stori.datamodel.repository.CreditReleaseRecordRepository;
import com.stori.datamodel.repository.CreditUsedRecordRepository;
import com.stori.recordfacade.CreditRecordServiceBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

@Service
@SofaService(uniqueId = "creditRecordService")
public class CreditRecordService implements CreditRecordServiceBase {
    private final static Logger logger = LogManager.getLogger(CreditRecordService.class);
    @Resource
    private CreditUsedRecordRepository creditUsedRecordRepository;

    @Resource
    private CreditReleaseRecordRepository creditReleaseRecordRepository;

    @Resource
    private CreditCardRepository creditCardRepository;

    private CreditCard getCreditCard(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if (optionalCreditCard.isPresent()) {
            return optionalCreditCard.get();
        } else {
            logger.error("Failed to find credit card by id: " + creditCardId);
            return null;
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
    public void addCreditUsedRecord(Long creditCardId, int creditUsed) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if(creditCard==null) {
            logger.warn("Failed to add used credit");
            return ;
        }
        CreditUsedRecord creditUsedRecord = new CreditUsedRecord(creditCard, creditUsed);
        creditUsedRecordRepository.save(creditUsedRecord);
        logger.info("Add credit used record");
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addCreditReleaseRecord(Long creditCardId, int creditReleased) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if(creditCard==null) {
            logger.warn("Failed to add used credit");
            return ;
        }
        CreditReleasedRecord creditReleaseRecord = new CreditReleasedRecord(creditCard, creditReleased);
        creditReleaseRecordRepository.save(creditReleaseRecord);
        logger.info("Add credit released record");
    }
}
