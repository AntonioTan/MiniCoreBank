package com.stori.bankuserservice.util;

import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.repository.CreditCardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class BankUserServiceUtil {
    private static final Logger logger = LogManager.getLogger();

    @Resource
    private CreditCardRepository creditCardRepository;

    @Transactional(timeout = 30)
    public CreditCard getCreditCardById(long creditCardId) {
        Optional<CreditCard> optionCreditCard = creditCardRepository.findById(creditCardId);
        if(!optionCreditCard.isPresent()) {
            logger.error("Credit card ID " + creditCardId + " could not be found!");
            return null;
        } else {
            CreditCard creditCard = optionCreditCard.get();
            return creditCard;
        }
    }
}
