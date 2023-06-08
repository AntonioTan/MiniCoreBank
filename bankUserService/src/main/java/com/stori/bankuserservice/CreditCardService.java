package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.repository.CreditCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service("creditCardService")
@SofaService(uniqueId = "creditCardService")
public class CreditCardService implements CreditCardServiceBase {
    @Resource
    private CreditCardRepository creditCardRepository;

    private final static Logger logger = LogManager.getLogger(CreditCard.class);

    private CreditCard getCreditCard(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if (!optionalCreditCard.isPresent()) {
            logger.error("Failed to get credit card by id: " + creditCardId);
            return null;
        } else {
            return optionalCreditCard.get();
        }
    }

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean setCreditCardStatus(long creditCardId, CreditCardStatus status) {
        int edited = creditCardRepository.setCreditCardStatus(creditCardId, status);
        if (edited == 1) {
            logger.info("Successfully set the status of the credit card with id: " + creditCardId + " to " + status);
            return true;
        } else {
            logger.info("Failed to set status for card with id: " + creditCardId);
            return false;
        }
    }

    @Override
    public CreditCardStatus getStatus(long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            return null;
        } else {
            return creditCard.getCreditCardStatus();
        }

    }

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public boolean setCreditLimit(long creditCardId, int creditLimit) {
        int edited = creditCardRepository.setCreditLimit(creditCardId, creditLimit);
        if (edited == 1) {
            logger.info("Successfully set credit limit for card with id: " + creditCardId);
            return true;
        } else {
            logger.info("Failed to set credit limit for card with id: " + creditCardId);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true, timeout = 30)
    public int getCreditLimit(long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            return -1;
        } else {
            return creditCard.getCreditLimit();
        }
    }
}
