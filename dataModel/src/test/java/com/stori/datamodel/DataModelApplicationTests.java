package com.stori.datamodel;

import com.stori.datamodel.repository.BillRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataModelApplicationTests {

    @Autowired
    private BillRepository billRepository;

    @Test
    void contextLoads() {
        boolean exist = billRepository.existsById(123L);
        Assertions.assertFalse(exist);
    }

}
