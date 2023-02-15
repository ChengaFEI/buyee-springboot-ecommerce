package com.chengfei.buyee.common.entity;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.chengfei.buyee.common.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String password;
    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;
    @Column(length = 64)
    private String photo;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), 
    	       inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    // Constructors
    public User() {}
    public User(String email, String password, String firstName, String lastName) {
	this.email = email;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
    }
    // Getters and Setters
    // ID
    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    // Email
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    // Password
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    // Name
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    @Transient
    public String getName() {return firstName + " " + lastName;}
    @Transient
    public String getFullName() {return firstName + " " + lastName;}
    // Photo
    public String getPhoto() {return photo;}
    public void setPhoto(String photo) {this.photo = photo;}
    @Transient
    public String getPhotoPathString() {
	if (id == null || photo == null) return Constants.S3_BASE_URI + "/user-photos/default-photo.png";
	return Constants.S3_BASE_URI + "/user-photos/" + this.getId() + "/" + this.getPhoto();
    }
    // Enabled
    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean enabled) {this.enabled = enabled;}
    // Roles
    public Set<Role> getRoles() {return roles;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public void addRole(Role role) {this.roles.add(role);}
    public boolean hasRole(String roleName) {
	Iterator<Role> iterator = roles.iterator();
	while (iterator.hasNext()) {
	    Role role = iterator.next();
	    if (role.getName().equals(roleName)) return true;
	}
	return false;
    }
    // Settings
    @Override
    public String toString() {
	return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + 
	       ", roles=" + roles + "]";
    }
}
