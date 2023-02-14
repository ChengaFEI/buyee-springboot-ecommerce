package com.chengfei.buyee.admin.user;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.chengfei.buyee.common.entity.Role;
import com.chengfei.buyee.common.entity.User;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE = 6;
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    // Create Tasks (empty)
    // Read Tasks
    public User readUserById(Integer id) throws UserNotFoundException {
	try {
	    return userRepo.findById(id).get();
	} catch (NoSuchElementException e) {
	    throw new UserNotFoundException("Could not find any user with ID " + id + ".");
	}
    }
    public User readUserByEmail(String email) throws UserNotFoundException {
	try {
	    return userRepo.readUserByEmail(email);
	} catch (NoSuchElementException e) {
	    throw new UserNotFoundException("Could not find any user wih email: " + email + ".");
	}
    }
    public Page<User> readUsersByPageNum(int pageNum, String sortField, String sortOrder, String keyword) {
	Pageable pageable = null;
	if (sortField != null && sortOrder != null) {
	    Sort sort = Sort.by(sortField);
	    sort = sortOrder.equals("asc") ? sort.ascending() : sort.descending();
	    pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
	} else {
	    pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE);
	}
	if (keyword != null)
	    return userRepo.readUsersByKeyword(keyword.trim(), pageable);
	return userRepo.findAll(pageable);
    }
    public List<User> readAllUsers() {
	return (List<User>) userRepo.findAll();
    }
    public List<Role> readAllRoles() {
	return (List<Role>) roleRepo.findAll();
    }
    // Update Tasks
    public User saveUser(User user) {
	Integer id = user.getId();
	boolean isUpdatingUser = id != null;
	boolean isKeepingPassword = user.getPassword().isEmpty();
	if (isUpdatingUser && isKeepingPassword) {
	    User existingUser = userRepo.findById(id).get();
	    user.setPassword(existingUser.getPassword());
	} else {
	    UserServiceUtil.encodePassword(user);
	}
	return userRepo.save(user);
    }
    public User updateAccount(User userInForm) {
	User userInDB = userRepo.findById(userInForm.getId()).get();
	if (!userInForm.getPassword().isEmpty()) {
	    userInDB.setPassword(userInForm.getPassword());
	    UserServiceUtil.encodePassword(userInDB);
	}
	if (userInForm.getPhoto() != null) {
	    userInDB.setPhoto(userInForm.getPhoto());
	}
	userInDB.setFirstName(userInForm.getFirstName());
	userInDB.setLastName(userInForm.getLastName());
	return userRepo.save(userInDB);
    }
    public void updateUserEnabledStatus(Integer id, boolean enabled) {
	userRepo.updateUserEnabledStatus(id, enabled);
    }
    // Delete Tasks
    public void deleteUserById(Integer id) throws UserNotFoundException {
	Long countById = userRepo.countUserById(id);
	if (countById == null || countById == 0) {
	    throw new UserNotFoundException("Could not find any user with ID " + id + ".");
	}
	userRepo.deleteById(id);
    }
    // Validate Tasks
    public boolean isEmailUnique(Integer id, String email) {
	User user = userRepo.readUserByEmail(email);
	if (user == null) return true;
	boolean isCreatingNew = id == null;
	if (isCreatingNew) return false;
	if (user.getId() != id) return false;
	return true;
    }
}
