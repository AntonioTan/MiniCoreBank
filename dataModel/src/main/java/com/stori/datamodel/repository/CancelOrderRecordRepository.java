package com.stori.datamodel.repository;

import com.stori.datamodel.model.CancelOrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface CancelOrderRecordRepository extends JpaRepository<CancelOrderRecord, Long> {
}
