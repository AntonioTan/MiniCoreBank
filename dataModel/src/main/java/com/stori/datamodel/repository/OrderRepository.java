package com.stori.datamodel.repository;

import com.stori.datamodel.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Resource;

@Resource
public interface OrderRepository extends JpaRepository<Order, Long> {
}
