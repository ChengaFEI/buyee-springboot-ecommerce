package com.chengfei.buyee.product;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.chengfei.buyee.common.entity.Product;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTests {
    @Autowired private ProductRepository repo;
    @Test
    public void testReadProductByAlias() {
	String alias1 = "Neewer-TT560-Flash-Speedlite";
	Product product1 = repo.readProductByAlias(alias1);
	assertNotNull(product1);
	System.out.println(product1);
    }
}
