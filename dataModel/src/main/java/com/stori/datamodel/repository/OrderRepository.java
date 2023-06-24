package com.stori.datamodel.repository;

import com.stori.datamodel.model.BizOrder;
import com.stori.datamodel.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface OrderRepository extends JpaRepository<BizOrder, Long> {
}
