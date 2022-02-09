package com.shopme.admin.service;

import java.util.List;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

public interface UserService {
	public List<User> listAll();
	public List<Role>listRoles();
	public void save(User user);
	public boolean isEmailUnique(Integer id,String email);
	public User get(Integer id);
	public void delete(Integer id)throws UserNotFoundException;
	public void updateEnabledStatus(Integer id,boolean enabled);
}
