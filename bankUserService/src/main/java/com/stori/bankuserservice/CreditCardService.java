package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardBase;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.repository.CreditCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service("creditCardService")
@SofaService(uniqueId = "creditCardService")
public class CreditCardService implements CreditCardBase {
    @Resource
    private CreditCardRepository creditCardRepository;

    private final static Logger logger = LogManager.getLogger(CreditCard.class);

    @Override
    public boolean setCreditCardStatus(long creditCardId, CreditCardStatus status) {
        return false;
    }

    @Override
    public CreditCardStatus getStatus(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if(!optionalCreditCard.isPresent()) {
            logger.error("Failed to get credit card by id: " + creditCardId);
            return null;
        } else {
            CreditCard creditCard = optionalCreditCard.get();
            return creditCard.getCreditCardStatus();
        }
    }

    @Transactional(timeout = 30)
    @Override
    public boolean setCreditLimit(long creditCardId, int creditLimit) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if (!optionalCreditCard.isPresent()) {
            logger.error("Failed to get credit card by id: " + creditCardId);
            return false;
        } else {
            CreditCard creditCard = optionalCreditCard.get();
            creditCard.setCreditLimit(creditLimit);
            creditCardRepository.save(creditCard);
            logger.info("Successfully set credit limit for card with id: " + creditCardId);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true, timeout = 30)
    public int getAvailableCredit(long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if(!optionalCreditCard.isPresent()) {
            logger.error("Failed to get credit card by id: " + creditCardId);
            return -1;
        } else {
            CreditCard creditCard = optionalCreditCard.get();
            return creditCard.getCreditLimit();
        }
    }
}
