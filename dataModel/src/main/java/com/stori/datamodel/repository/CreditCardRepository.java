package com.stori.datamodel.repository;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.CreditCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
}
