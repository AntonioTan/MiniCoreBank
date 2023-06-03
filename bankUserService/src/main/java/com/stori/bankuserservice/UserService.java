package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardServiceBase;
import com.stori.bankuserservicefacade.UserServiceBase;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.User;
import com.stori.datamodel.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;


@SofaService(uniqueId = "userService")
@Service("userService")
public class UserService implements UserServiceBase {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardServiceBase creditCardService;

    @Resource
    private UserRepository userRepository;

    @Override
    public Long addUser(String name) {
        User user = new User(name);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
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
    public Long addCreditCard(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()) {
            logger.error("Failed to find user by id: " + userId);
            return (long) -1;
        } else {
            CreditCard creditCard = new CreditCard();
            User user = optionalUser.get();
            user.setCreditCard(creditCard);
            creditCard.setUser(user);
            userRepository.save(user);
            return user.getCreditCard().getId();
        }
    }
}
