package com.chengfei.buyee.admin.brand;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.chengfei.buyee.admin.user.UserNotFoundException;
import com.chengfei.buyee.common.entity.Brand;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class BrandService {
    public static final Integer BRANDS_PER_PAGE = 8;
    @Autowired 
    private BrandRepository repo;
    // Create Tasks
    public void saveBrand(Brand brand) {
	repo.save(brand);
    }
    // Read Tasks
    public Brand readBrandById(Integer id) throws BrandNotFoundException {
	try {
	    return repo.findById(id).get();
	} catch (NoSuchElementException e) {
	    throw new BrandNotFoundException("Could not find any brand with ID " + id + ".");
	}
    }
    public Page<Brand> readBrandsByPageNum(int pageNum, String sortField, String sortOrder, String keyword) {
	Pageable pageable = null;
	if (sortField != null && sortOrder != null) {
	    Sort sort = Sort.by(sortField);
	    sort = sortOrder.equals("asc") ? sort.ascending() : sort.descending();
	    pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE, sort);
	} else {
	    pageable = PageRequest.of(pageNum - 1, BRANDS_PER_PAGE);
	}
	if (keyword != null)
	    return repo.readBrandsByKeyword(keyword.trim(), pageable);
	return repo.findAll(pageable);
    }
    public List<Brand> readAllBrands() {
	return (List<Brand>) repo.findAll();
    }
    public List<Brand> readAllBrandsIdNameAscByName() {
	return repo.readAllBrandsIdNameAscByName();
    }
    // Delete Tasks
    public void deleteBrandById(Integer id) throws UserNotFoundException {
	Long countById = repo.countBrandById(id);
	if (countById == null || countById == 0) {
	    throw new UserNotFoundException("Could not find any brand with ID " + id + ".");
	}
	repo.deleteById(id);
    }
    // Validate Tasks
    public boolean isNameUnique(Integer id, String name) {
	Brand brand = repo.findByName(name);
	if (brand == null) return true;
	boolean isCreatingNew = id == null;
	if (isCreatingNew) return false;
	if (brand.getId() == id) return true;
	return false;
    }
}
