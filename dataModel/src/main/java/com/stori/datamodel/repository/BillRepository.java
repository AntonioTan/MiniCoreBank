package com.stori.datamodel.repository;

import com.stori.datamodel.model.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Integer> {
}
