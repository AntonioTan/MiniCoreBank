package com.stori.datamodel.repository;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
