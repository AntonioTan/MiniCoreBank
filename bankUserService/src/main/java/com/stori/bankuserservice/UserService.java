package com.stori.bankuserservice;

import com.stori.bankuserservice.util.BankUserServiceUtil;
import com.stori.bankuserservicefacade.CreditCardStatus;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.User;
import com.stori.datamodel.repository.CreditCardRepository;
import com.stori.datamodel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UserService implements UserServiceBase {

    private static final Logger logger = LogManager.getLogger();
    private static final BankUserServiceUtil bankUserServiceUtil = new BankUserServiceUtil();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;


    @Override
    public boolean addUser(String name) {
        // TODO
        return false;
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

    @Override
    @Transactional(readOnly = true, timeout = 30)
    public int getAccountBalance(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            logger.error("Failed to find user by id: " + userId);
            return -1;
        } else {
            User user = optionalUser.get();
            return user.getBalance();
        }
    }

    @Override
    @Transactional(timeout = 30)
    public boolean addCreditCard(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            logger.error("Failed to find user by id: " + userId);
            return false;
        } else {
            CreditCard creditCard = new CreditCard();
            User user = optionalUser.get();
            user.setCreditCard(creditCard);
            creditCard.setUser(user);
            userRepository.save(user);
            return false;
        }
    }
}
