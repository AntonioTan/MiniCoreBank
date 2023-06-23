package com.stori.creditservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.creditfacade.CreditService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.Money;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;
import com.stori.datamodel.repository.CreditCardRepository;
import com.stori.recordfacade.RecordService;
import com.stori.util.AssertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@SofaService(uniqueId = "creditService")
public class CreditServiceImpl implements CreditService {
    private final static Logger logger = LoggerFactory.getLogger(CreditServiceImpl.class);
    @Resource
    private CreditCardRepository creditCardRepository;

    @SofaReference(uniqueId = "creditUsedRecordService")
    RecordService<CreditUsedRecord> creditUsedRecordService;

    @SofaReference(uniqueId = "creditReleasedRecordService")
    RecordService<CreditReleasedRecord> creditReleasedRecordService;

    @SofaReference(uniqueId = "creditCardService")
    CreditCardService creditCardService;

    private CreditCard getCreditCard(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        return AssertUtil.getOptional(optionalCreditCard);
//        if (optionalCreditCard.isPresent()) {
//            return optionalCreditCard.get();
//        } else {
//            logger.error("Failed to find credit card by id: " + creditCardId);
//            return null;
//        }
    }


    @Override
    @Transactional(timeout = 30, rollbackFor = {Exception.class}, isolation = Isolation.REPEATABLE_READ)
    public Boolean updateCreditUsed(Long creditCardId, Money creditUsed, Long requestId) {
        // request id (unique)
        int foundRequest = creditUsedRecordService.findByRequestId(requestId);
        if (foundRequest != 0) {
            logger.warn("Credit card with id: " + creditCardId + " failed to add " + creditUsed.getNumber() + " used credit due to duplicate request!");
            return false;
        }
        CreditCard creditCard = creditCardRepository.selectCreditCardForUpdate(creditCardId);
        Money curCreditUsed = creditCard.getCreditUsed();
        Money curCreditLimit = creditCard.getCreditLimit();
        if (creditCard.getCreditCardStatus() != CreditCardStatusEnum.ACTIVE) {
            logger.warn("Credit card with id: " + creditCardId + " failed to add " + creditUsed.getNumber() + " used credit due to inactive card status!");
            return false;
        }

        if (curCreditUsed.getNumber() + creditUsed.getNumber() <= curCreditLimit.getNumber()) {
            creditCard.setCreditUsed(new Money(curCreditUsed.getNumber() + creditUsed.getNumber()));
            if (logger.isDebugEnabled()) logger.info("Added {} used credit to credit card with id: {}", creditUsed, creditCardId);
            CreditUsedRecord creditUsedRecord = new CreditUsedRecord(creditCard, creditUsed, requestId);
            creditUsedRecordService.saveRecord(creditUsedRecord);
            if(logger.isDebugEnabled()) logger.info("Saved credit used record");
            creditCardRepository.saveAndFlush(creditCard);
            return true;
        } else {
            logger.warn("Credit card with id: {} failed to add {} used credit", creditCardId, creditUsed.getNumber());
            return false;
        }
    }

    @Override
    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    public Boolean updateCreditReleased(Long creditCardId, Money creditReleased, Long requestId) {
        int foundRequest = creditReleasedRecordService.findByRequestId(requestId);
        if (foundRequest != 0) {
            logger.warn("Credit card with id: {} failed to add {} released credit due to duplicate request!", creditCardId, creditReleased.getNumber());
            return false;
        }
        CreditCard creditCard = creditCardRepository.selectCreditCardForUpdate(creditCardId);
        if (creditCard.getCreditCardStatus() != CreditCardStatusEnum.ACTIVE) {
            logger.warn("Credit card with id: {} failed to add {} released credit due to inactive status!", creditCardId ,creditReleased.getNumber());
            return false;
        }
//        int edited = creditCardRepository.addCreditUsed(creditCardId, -creditReleased, CreditCardStatus.ACTIVE);
        Money curCreditUsed = creditCard.getCreditUsed();
        creditCard.setCreditUsed(new Money(curCreditUsed.getNumber() - creditReleased.getNumber()));
        if(logger.isDebugEnabled()) logger.info("Added {} to credit card with id {}",creditReleased ,creditCardId);
        CreditReleasedRecord creditReleasedRecord = new CreditReleasedRecord(creditCard, creditReleased, requestId);
        creditReleasedRecordService.saveRecord(creditReleasedRecord);
        creditCardRepository.saveAndFlush(creditCard);
        return true;
    }

    @Override
    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    public Money getRemainingCredit(Long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            logger.warn("Failed to add released credit");
            return null;
        }
        Money creditLimit = creditCard.getCreditLimit();
        Money creditUsed = creditCard.getCreditUsed();
        if(logger.isDebugEnabled()) logger.info("Credit card with id: {} has remaining credit: {}", creditCardId, (creditLimit.getNumber() - creditUsed.getNumber()));
        return new Money(creditLimit.getNumber() - creditUsed.getNumber());
    }
}
