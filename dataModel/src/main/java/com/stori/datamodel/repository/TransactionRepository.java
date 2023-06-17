package com.stori.datamodel.repository;

import com.stori.datamodel.model.BizOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<BizOrder, Long> {
}
