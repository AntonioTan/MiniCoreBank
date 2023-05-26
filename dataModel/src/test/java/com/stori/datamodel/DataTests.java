package com.stori.datamodel;


import com.stori.datamodel.model.User;
import com.stori.datamodel.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DataTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void addUser() {
        User user = new User("Tianyi");
        userRepository.save(user);
    }

}
