package com.shopme.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateNewUserWithOneRole() {
		Role admin = entityManager.find(Role.class, 1);
		User user1 = new User("anand@chess.com", "Vishwnathan", "Anand", "anand");
		user1.addRoles(admin);

		User savedUser = repo.save(user1);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUserWithTwoRole() {
		User user2 = new User("carlsen@chess.com", "Magnus", "Carlsen", "carlsen");
		Role editor = new Role(3);
		Role assistant = new Role(5);
		user2.addRoles(assistant);
		user2.addRoles(editor);
		User savedUser = repo.save(user2);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		repo.findAll().forEach(user -> System.out.println(user));
	}

	@Test
	public void testUserById() {
		User user1 = repo.findById(1).get();
		assertThat(user1.getEmail()).isEqualTo("anand@chess.com");
	}

	@Test
	public void testUpdateUser() {
		User user1 = repo.findById(1).get();
		user1.setEnabled(true);
		User updatedUser = repo.save(user1);
		assertThat(updatedUser.getId()).isEqualTo(1);
	}

	@Test
	public void testUpdateUserRoles() {
		User user1 = repo.findById(2).get();
		Role editor = new Role(3);
		Role salesperson = new Role(2);
		user1.getRoles().remove(editor);
		user1.addRoles(salesperson);
		User updatedUser = repo.save(user1);
		assertThat(updatedUser.getId()).isEqualTo(2);
	}
	@Test
	public void testDeleteUser() {
		repo.deleteById(2);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email="anand@chess.com";
		User user=repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id=1;
		Long countById=repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
}
