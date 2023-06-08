package com.stori.creditservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.creditfacade.CreditServiceBase;
import com.stori.creditservice.exception.AddCreditUsedException;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;
import com.stori.datamodel.repository.CreditCardRepository;
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
@SofaService(uniqueId = "creditService")
public class CreditService implements CreditServiceBase {
    private final static Logger logger = LogManager.getLogger(CreditService.class);
    @Resource
    private CreditCardRepository creditCardRepository;

    @SofaReference(uniqueId = "creditRecordService")
    CreditRecordServiceBase creditRecordService;

    @SofaReference(uniqueId = "creditCardService")
    CreditCardServiceBase creditCardService;

    private CreditCard getCreditCard(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if (optionalCreditCard.isPresent()) {
            return optionalCreditCard.get();
        } else {
            logger.error("Failed to find credit card by id: " + creditCardId);
            return null;
        }
    }


    @Override
    @Transactional(timeout = 30, rollbackFor = {Exception.class}, isolation = Isolation.REPEATABLE_READ)
    public void addCreditUsed(long creditCardId, int creditUsed) {
        int edited = creditCardRepository.addCreditUsed(creditCardId, creditUsed, CreditCardStatus.ACTIVE);
        if (edited == 1) {
            logger.info("Added " + creditUsed + " to credit card with id: " + creditCardId);
            creditRecordService.addCreditUsedRecord(creditCardId, creditUsed);
            logger.info("Saved credit used record");
        } else {
            logger.warn("Credit card with id: " + creditCardId + " failed to add " + creditUsed + " used credit");
        }
    }

    @Override
    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    public void releaseCredit(long creditCardId, int creditReleased) {
        int edited = creditCardRepository.addCreditUsed(creditCardId, -creditReleased, CreditCardStatus.ACTIVE);
        if(edited == 1) {
            logger.info("Added " + creditReleased + " to credit card with id " + creditCardId);
            creditRecordService.addCreditReleaseRecord(creditCardId, creditReleased);
        } else {
            logger.warn("Credit card with id: " + creditCardId + " failed to add " + creditReleased + " released credit");
        }
    }

    @Override
    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    public int getRemainingCredit(long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            logger.warn("Failed to add released credit");
            return -1;
        }
        int creditLimit = creditCard.getCreditLimit();
        int creditUsed = creditCard.getCreditUsed();
        logger.info("Credit card with id: " + creditCardId + " has remaining credit: " + (creditLimit - creditUsed));
        return creditLimit - creditUsed;
    }
}
