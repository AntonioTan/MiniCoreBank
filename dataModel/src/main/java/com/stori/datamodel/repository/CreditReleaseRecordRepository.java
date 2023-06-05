package com.stori.datamodel.repository;

import com.stori.datamodel.model.CreditReleasedRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface CreditReleaseRecordRepository extends CrudRepository<CreditReleasedRecord, Long> {
}
