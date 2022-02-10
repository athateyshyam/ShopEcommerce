package com.shopme.admin.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import com.shopme.admin.service.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> listAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public List<Role> listRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public User save(User user) {
		boolean isUpdatingUser=(user.getId()!=null);
		if(isUpdatingUser) {
			User existingUser=userRepository.findById(user.getId()).get();
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
				encodePassword(user);
			}
		}else {
			encodePassword(user);
		}
		return userRepository.save(user);
	}

	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	@Override
	public boolean isEmailUnique(Integer id,String email) {
		User user = userRepository.getUserByEmail(email);
		if(user==null)return true;
		boolean isCreatingNew=(id==null);
		if(isCreatingNew) {
			if(user!=null)return false;
		}else {
			if(user.getId()!=id) {
				return false;
			}
		}
		return true;
	}

	@Override
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}

	@Override
	public void delete(Integer id) throws UserNotFoundException {
		Long countById=userRepository.countById(id);
		if(countById==null||countById==0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		userRepository.deleteById(id);
	}

	@Override
	public void updateEnabledStatus(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}
}
