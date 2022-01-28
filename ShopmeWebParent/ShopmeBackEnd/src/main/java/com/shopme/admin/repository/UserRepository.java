package com.shopme.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
