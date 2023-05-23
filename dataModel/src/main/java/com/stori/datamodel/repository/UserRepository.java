package com.stori.datamodel.repository;

import com.stori.datamodel.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
