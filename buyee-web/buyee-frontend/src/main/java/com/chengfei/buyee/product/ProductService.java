package com.chengfei.buyee.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chengfei.buyee.common.entity.Product;
@Service
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 10000;
    @Autowired private ProductRepository repo;
    //Read Tasks
    public Page<Product> readProductsByCategory(int pageNum, Integer categoryId) {
	String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
	Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
	return repo.readProductsByCategory(categoryId, categoryIdMatch, pageable);
    }
}
