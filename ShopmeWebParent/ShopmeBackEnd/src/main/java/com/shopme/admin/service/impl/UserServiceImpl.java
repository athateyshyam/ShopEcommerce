package com.shopme.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.repository.UserRepository;
import com.shopme.admin.service.UserService;
import com.shopme.common.entity.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Override
	public List<User> listAll() {
		return (List<User>) repository.findAll();
	}

}
