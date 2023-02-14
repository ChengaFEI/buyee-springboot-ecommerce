package com.chengfei.buyee.admin.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.chengfei.buyee.admin.user.UserRepository;
import com.chengfei.buyee.common.entity.User;
public class BuyeeUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	User user = userRepo.readUserByEmail(email);
	if (user != null)
	    return new BuyeeUserDetails(user);
	throw new UsernameNotFoundException("Could not find user with email: " + email);
    }
}
