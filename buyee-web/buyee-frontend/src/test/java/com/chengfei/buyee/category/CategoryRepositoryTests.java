package com.chengfei.buyee.category;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.chengfei.buyee.common.entity.Category;
@DataJpaTest 
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CategoryRepositoryTests {
    @Autowired private CategoryRepository repo;
    @Test
    public void testReadEnabledCategories() {
	List<Category> listCategories1 = repo.readEnabledCategories();
	listCategories1.forEach(category -> {
	    System.out.println(category.getName() + " (" + category.isEnabled() + ")");
	});
	assertNotNull(listCategories1);
    }
}
