package com.chengfei.buyee.common.entity;
import java.util.HashSet;
import java.util.Set;

import com.chengfei.buyee.common.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 64, nullable = false, unique = true)
    private String alias;
    @Column(length = 128)
    private String image; 
    private boolean enabled;
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    @Column(name = "all_parent_ids", length = 512, nullable = true)
    private String allParentIds;
    @OneToMany(mappedBy = "parent")
    @OrderBy("name asc")
    private Set<Category> children = new HashSet<>();
    private Integer level;
    // Constructors
    public Category() {
    }
    public Category(Integer id) {
	this.id = id;
    }
    public Category(String name) {
	this.name = name;
	this.alias = name;
    }
    public Category(Integer id, String name) {
	this.id = id;
	this.name = name;
    }
    public Category(Integer id, String name, String alias) {
	this.id = id;
	this.name = name;
	this.alias = alias;
    }
    public Category(String name, Category parent) {
	this(name);
	this.parent = parent;
    }
    public Category(Integer id, String name, String alias, String image, boolean enabled, Integer level,
	    Category parent) {
	this.id = id;
	this.name = name;
	this.alias = alias;
	this.image = image;
	this.enabled = enabled;
	this.level = level;
	this.parent = parent;
    }
    public Category(Integer id, String name, String alias, String image, boolean enabled, Integer level,
	    Category parent, Set<Category> children) {
	this.id = id;
	this.name = name;
	this.alias = alias;
	this.image = image;
	this.enabled = enabled;
	this.level = level;
	this.parent = parent;
	this.children = children;
    }
    // Getters and Setters
    	// Id
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    	// Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    	// Alias
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    	// Image
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImagePathString() {
	if (id == null || image == null) return Constants.S3_BASE_URI + "/category-images/default-image.png";
	return Constants.S3_BASE_URI + "/category-images/" + this.getId() + "/" + this.getImage();
    }
    	// Enabled
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    	// Parent
    public Category getParent() {
        return parent;
    }
    public void setParent(Category parent) {
        this.parent = parent;
    }
    public Integer getParentId() {
	return this.parent.getId();
    }
    public Integer getParentLevel() {
	return this.parent.getLevel();
    }
    	// All Parent IDs
    public String getAllParentIds() {
	return allParentIds;
    }
    public void setAllParentIds(String allParentIds) {
	this.allParentIds = allParentIds;
    }
    	// Children
    public Set<Category> getChildren() {
        return children;
    }
    public void setChildren(Set<Category> children) {
        this.children = children; 
    }
    	// Level
    public Integer getLevel() {
	return this.level;
    }
    public void setLevel(Integer level) {
	this.level = level;
    }
    @Override
    public String toString() {
	return this.name;
    }
}
