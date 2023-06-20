package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.repository.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service("creditCardService")
@SofaService(uniqueId = "creditCardService")
public class CreditCardServiceImpl implements CreditCardService {
    @Resource
    private CreditCardRepository creditCardRepository;

    private final static Logger logger = LoggerFactory.getLogger(CreditCard.class);

    @Override
    public CreditCard getCreditCard(Long creditCardId) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(creditCardId);
        if (!optionalCreditCard.isPresent()) {
            logger.error("Failed to get credit card by id: {}", creditCardId);
            return null;
        } else {
            return optionalCreditCard.get();
        }
    }

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Boolean updateCreditCardStatus(Long creditCardId, CreditCardStatusEnum status) {
        int edited = creditCardRepository.setCreditCardStatus(creditCardId, status);
        if (edited == 1) {
            if(logger.isDebugEnabled()) logger.info("Successfully set the status of the credit card with id: {} to {}", creditCardId, status);
            return true;
        } else {
            if(logger.isDebugEnabled()) logger.info("Failed to set status for card with id: {}", creditCardId);
            return false;
        }
    }

    @Override
    public CreditCardStatusEnum getStatus(Long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            return null;
        } else {
            return creditCard.getCreditCardStatus();
        }

    }

    @Transactional(timeout = 30, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Boolean setCreditLimit(Long creditCardId, Integer creditLimit) {
        int edited = creditCardRepository.setCreditLimit(creditCardId, creditLimit, CreditCardStatusEnum.ACTIVE);
        if (edited == 1) {
            if(logger.isDebugEnabled()) logger.info("Successfully set credit limit for card with id: {}", creditCardId);
            return true;
        } else {
            if(logger.isDebugEnabled()) logger.info("Failed to set credit limit for card with id: {}", creditCardId);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true, timeout = 30)
    public Integer getCreditLimit(Long creditCardId) {
        CreditCard creditCard = getCreditCard(creditCardId);
        if (creditCard == null) {
            return -1;
        } else {
            return creditCard.getCreditLimit();
        }
    }
}
