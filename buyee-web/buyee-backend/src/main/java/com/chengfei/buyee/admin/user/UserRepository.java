package com.chengfei.buyee.admin.user;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.chengfei.buyee.common.entity.User;
public interface UserRepository extends PagingAndSortingRepository<User, Integer>, CrudRepository<User, Integer> {
    // Read Tasks
    public Long countUserById(Integer id);
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User readUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
    public Page<User> readUsersByKeyword(String keyword, Pageable pageable);
    // Update Tasks
    @Modifying
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    public void updateUserEnabledStatus(Integer id, boolean enabled);
}
