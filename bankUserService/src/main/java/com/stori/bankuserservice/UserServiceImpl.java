package com.stori.bankuserservice;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.bankuserservicefacade.CreditCardService;
import com.stori.bankuserservicefacade.UserService;
import com.stori.datamodel.model.CreditCard;
import com.stori.datamodel.model.User;
import com.stori.datamodel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;


@SofaService(uniqueId = "userService")
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @SofaReference(uniqueId = "creditCardService")
    private CreditCardService creditCardService;

    @Resource
    private UserRepository userRepository;

    @Override
    public Long saveUser(String name) {
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
    public Integer getAccountBalance(Long userId) {
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
    public Long saveCreditCard(Long userId) {
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
