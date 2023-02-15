package com.chengfei.buyee.admin.product;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.chengfei.buyee.common.entity.Product;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class ProductService {
    public static final Integer PRODUCTS_PER_PAGE = 5;
    @Autowired private ProductRepository repo;
    // Create Tasks
    public Product saveProduct(Product product) {
	Date currentTime = new Date();
	Integer id = product.getId();
	if (id == null) product.setCreatedTime(currentTime);
	else {
	    Product productInDB = repo.findById(id).get();
	    product.setCreatedTime(productInDB.getCreatedTime());
	}
	product.setUpdatedTime(currentTime);
	if (product.getAlias() == null || product.getAlias().isEmpty()) {
	    String defaultAlias = product.getName().toLowerCase().replaceAll(" ", "_");
	    product.setAlias(defaultAlias);
	} else product.setAlias(product.getAlias().toLowerCase().replaceAll(" ", "_"));
	return repo.save(product);
    }
    public Product saveProductPrice(Product productInForm) {
	Product productInDB = repo.findById(productInForm.getId()).get();
	if (productInDB == null) return null;
	productInDB.setPrice(productInForm.getPrice());
	productInDB.setCost(productInForm.getCost());
	productInDB.setDiscountPercent(productInForm.getDiscountPercent());
	return repo.save(productInDB);
    }
    // Read Tasks
    public Product readProductById(Integer id) throws ProductNotFoundException {
	try {
	    return repo.findById(id).get();
	} catch (NoSuchElementException e) {
	    throw new ProductNotFoundException("Could not find any product with ID " + id + ".");
	}
    }
    public Page<Product> readProductsByPageNum(
	    int pageNum, String sortField, String sortOrder, 
	    String keyword, Integer categoryId) {
	Pageable pageable = null;
	if (sortField != null && sortOrder != null) {
	    Sort sort = Sort.by(sortField);
	    sort = sortOrder.equals("asc") ? sort.ascending() : sort.descending();
	    pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
	} else pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
	boolean keywordExist = keyword != null && !keyword.isEmpty();
	boolean categoryIdExist = categoryId != null && categoryId > 0;
	if (categoryIdExist) {
	    String categoryIdMatch = "-" + categoryId + "-";
	    if (keywordExist) return repo.readProductsByKeywordParentCategory(
		    keyword.trim(), categoryId, categoryIdMatch, pageable);
	    else return repo.readProductsByParentCategory(categoryId, categoryIdMatch, pageable);
	} else if (keywordExist) return repo.readProductsByKeyword(keyword.trim(), pageable);
	return repo.findAll(pageable);
    }
    public List<Product> readAllProducts() {
	return (List<Product>) repo.findAll();
    }
    // Update Tasks
    public void updateProductEnabledStatus(Integer id, boolean enabled) {
	repo.updateProductEnabledStatus(id, enabled);
    }
    // Delete Tasks
    public void deleteProductById(Integer id) throws ProductNotFoundException {
	Long countById = repo.countProductById(id);
	if (countById == null || countById == 0) {
	    throw new ProductNotFoundException("Could not find any product with ID " + id + ".");
	}
	repo.deleteById(id);
    }
    // Validate Tasks
    public boolean isNameUnique(Integer id, String name) {
	Product product = repo.findByName(name);
	if (product == null) return true;
	boolean isCreatingNew = id == null;
	if (isCreatingNew) return false;
	if (product.getId() == id) return true;
	return false;
    }
}
