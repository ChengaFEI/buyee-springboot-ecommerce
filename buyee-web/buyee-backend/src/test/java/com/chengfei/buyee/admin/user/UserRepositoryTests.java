package com.chengfei.buyee.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Role;
import com.chengfei.buyee.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    UserRepository repo;

    @Autowired
    TestEntityManager entityManager;

    // Create Tasks

    @Test
    public void testCreateUserWithOneRole() {
	Role roleAdmin = entityManager.find(Role.class, 2);
	User userCF = new User("Cheng_a_Fei@outlook.com", "123456", "Cheng", "Fei");
	userCF.addRole(roleAdmin);
	User savedUser = repo.save(userCF);
	assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
	User userJC = new User("jackie.chan@outlook.com", "888888", "Jackie", "Chan");
	Role roleEditor = new Role(1);
	Role roleAssistant = new Role(3);
	userJC.addRole(roleEditor);
	userJC.addRole(roleAssistant);
	User savedUser = repo.save(userJC);
	assertThat(savedUser.getId()).isGreaterThan(0);
    }

    // Read Tasks

    @Test
    public void testReadUserById() {
	User userCF = repo.findById(1).get();
	System.out.println(userCF);
	assertThat(userCF).isNotNull();
    }

    @Test
    public void testCountUserById() {
	Integer id1 = 5;
	Integer id2 = 1000;
	assertThat(repo.countUserById(id1)).isNotNull().isGreaterThan(0);
	assertEquals(repo.countUserById(id2), 0);
    }

    @Test
    public void testReadUserByEmail() {
	User user1 = repo.readUserByEmail("Cheng_a_Fei@outlook.com");
	User user2 = repo.readUserByEmail("ThisIs@NotAReal.Email");
	assertThat(user1).isNotNull();
	assertThat(user2).isNull();
    }

    @Test
    public void testReadUsersByKeyword() {
	String keyword = "Chan";
	int pageNumber = 0;
	int pageSize = 4;
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	Page<User> page = repo.readUsersByKeyword(keyword, pageable);
	List<User> users = page.getContent();
	users.forEach((user) -> {
	    System.out.println(user);
	});
	assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void testReadUsersInFirstPage() {
	int pageNumber = 0;
	int pageSize = 4;
	Pageable pageable = PageRequest.of(pageNumber, pageSize);
	Page<User> page = repo.findAll(pageable);
	List<User> users = page.getContent();
	assertEquals(users.size(), pageSize);
    }

    @Test
    public void testReadAllUsers() {
	Iterable<User> allUsers = repo.findAll();
	allUsers.forEach((user) -> System.out.println(user));
    }

    // Update Tasks

    @Test
    public void testUpdateUserDetails() {
	User userCF = repo.findById(1).get();
	userCF.setEnabled(true);
	userCF.setEmail("cf482@cornell.edu");
	User savedUser = repo.save(userCF);
	assertEquals(savedUser.isEnabled(), true);
	assertEquals(savedUser.getEmail(), "cf482@cornell.edu");
    }

    @Test
    public void testUpdateUserRoles() {
	User userJC = repo.findById(2).get();
	Role roleEditor = new Role(1);
	Role roleAdmin = new Role(2);
	Role roleAssistant = new Role(3);

	userJC.getRoles().remove(roleAssistant);
	userJC.addRole(roleAdmin);
	User savedUser = repo.save(userJC);
	System.out.println(userJC);

	assertEquals(savedUser.getRoles(), Set.of(roleEditor, roleAdmin));
    }

    @Test
    public void testUpdateUserEnabledStatus() {
	Integer id1 = 5;
	Integer id2 = 6;
	repo.updateUserEnabledStatus(id1, false);
	repo.updateUserEnabledStatus(id2, true);
	User user1 = repo.findById(id1).get();
	User user2 = repo.findById(id2).get();
	assertEquals(user1.isEnabled(), false);
	assertEquals(user2.isEnabled(), true);
    }

    // Delete Tasks

    @Test
    public void testDeleteUser() {
	repo.deleteById(1);
    }
}
