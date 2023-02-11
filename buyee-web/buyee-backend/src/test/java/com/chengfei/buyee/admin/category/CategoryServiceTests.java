package com.chengfei.buyee.admin.category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chengfei.buyee.common.entity.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {
    @MockBean
    private CategoryRepository repo;
    
    @InjectMocks
    private CategoryService service;
    
    @Test
    public void testIsNameAliasUnique() {
	Integer id1 = null;
	String name1 = "Computers";
	String alias1 = "abc";
	Category category1 = new Category(id1, name1, alias1);
	Mockito.when(repo.findByName(name1)).thenReturn(category1);
	assertEquals(service.isNameAliasUnique(id1, name1, alias1), "DuplicateName");
	
	Integer id2 = null;
	String name2 = "abc";
	String alias2 = "Computers";
	Category category2 = new Category(id2, name2, alias2);
	Mockito.when(repo.findByName(name2)).thenReturn(null);
	Mockito.when(repo.findByAlias(alias2)).thenReturn(category2);
	assertEquals(service.isNameAliasUnique(id2, name2, alias2), "DuplicateAlias");
	Mockito.when(repo.findByName(name2)).thenReturn(null);
	Mockito.when(repo.findByAlias(alias2)).thenReturn(null);
	assertEquals(service.isNameAliasUnique(id2, name2, alias2), "OK");
	
	Integer id3 = 1;
	String name3 = "abc";
	String alias3 = "Computers";
	Category category3 = new Category(id3, name3, alias3);
	Mockito.when(repo.findByName(name3)).thenReturn(category3);
	Mockito.when(repo.findByAlias(alias3)).thenReturn(null);
	assertEquals(service.isNameAliasUnique(id3, name3, alias3), "OK");
	Category category4 = new Category(2, name3, alias3);
	Mockito.when(repo.findByName(name3)).thenReturn(category4);
	Mockito.when(repo.findByAlias(alias3)).thenReturn(null);
	assertEquals(service.isNameAliasUnique(id3, name3, alias3), "DuplicateName");
    }
}
