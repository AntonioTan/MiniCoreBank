package com.stori.datamodel.repository;

import com.stori.datamodel.model.CreditUsedRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface CreditUsedRecordRepository extends CrudRepository<CreditUsedRecord, Long> {
}
