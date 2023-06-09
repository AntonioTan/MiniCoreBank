package com.stori.datamodel.repository;

import com.stori.datamodel.model.CreateOrderRecord;
import com.stori.datamodel.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface CreateOrderRecordRepository extends JpaRepository<CreateOrderRecord, Long> {
}
