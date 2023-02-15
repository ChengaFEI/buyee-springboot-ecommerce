package com.chengfei.buyee.admin.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.chengfei.buyee.common.entity.Role;
import com.chengfei.buyee.common.entity.User;
public class BuyeeUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private User user;
    // Constructors
    public BuyeeUserDetails(User user) {this.user = user;}
    // Getters and Setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	Set<Role> roles = user.getRoles();
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	for (Role role : roles) {authorities.add(new SimpleGrantedAuthority(role.getName()));}
	return authorities;
    }
    @Override
    public String getPassword() {return user.getPassword();}
    @Override
    public String getUsername() {return user.getEmail();}
    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return user.isEnabled();}
    public String getFullName() {return this.user.getFirstName() + " " + this.user.getLastName();}
    public void setFirstName(String firstName) {this.user.setFirstName(firstName);}
    public void setLastName(String lastName) {this.user.setLastName(lastName);}
    public boolean hasRole(String roleName) {return this.user.hasRole(roleName);}
}
