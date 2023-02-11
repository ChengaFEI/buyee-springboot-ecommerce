package com.chengfei.buyee.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateAdmin() {
	Role roleAdmin = new Role("Admin", "Manage everything.");
	Role savedRole = repo.save(roleAdmin);
	assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSalesperson() {
	Role roleSalesperson = new Role("Salesperson", "Manage product prices, customoers, shipping, orders, and sales support.");
	Role savedRole = repo.save(roleSalesperson);
	assertThat(savedRole.getId()).isGreaterThan(0);
    }
    
    @Test
    public void testCreateEditor() {
	Role roleEditor = new Role("Editor", "Manage categories, brands, products, articles, and menus.");
	Role savedRole = repo.save(roleEditor);
	assertThat(savedRole.getId()).isGreaterThan(0);
    }
    
    @Test
    public void testCreateShipper() {
	Role roleShipper = new Role("Shipper", "View products and orders, and update order status.");
	Role savedRole = repo.save(roleShipper);
	assertThat(savedRole.getId()).isGreaterThan(0);
    }
    
    @Test
    public void testCreateAssistant() {
	Role roleAssistant = new Role("Assistant", "Manage questions and reviews.");
	Role savedRole = repo.save(roleAssistant);
	assertThat(savedRole.getId()).isGreaterThan(0);
    }
}
