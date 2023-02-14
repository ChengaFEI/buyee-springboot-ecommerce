package com.chengfei.buyee.admin.brand;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.chengfei.buyee.common.entity.Brand;
public interface BrandRepository extends CrudRepository<Brand, Integer>, 
					 PagingAndSortingRepository<Brand, Integer>{
    // Read Tasks
    public Long countBrandById(Integer id);
    public Brand findByName(String name);
    @Query("SELECT NEW Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
    public List<Brand> readAllBrandsIdNameAscByName();
    @Query("SELECT b FROM Brand b WHERE CONCAT(b.id, ' ', b.name) LIKE %?1%")
    public Page<Brand> readBrandsByKeyword(String keyword, Pageable pageable);
}
