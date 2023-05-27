package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservice.util.BankUserServiceUtil;
import com.stori.bankuserservicefacade.CreditCardBase;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@SofaService(uniqueId = "userService")
@Service
public class UserService implements UserServiceBase {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private static final BankUserServiceUtil bankUserServiceUtil = new BankUserServiceUtil();

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardBase creditCardService;

    @Resource
    private UserRepository userRepository;

    @Override
    public Long addUser(String name) {
        User user = new User(name);
        userRepository.save(user);
        return user.getId();
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
