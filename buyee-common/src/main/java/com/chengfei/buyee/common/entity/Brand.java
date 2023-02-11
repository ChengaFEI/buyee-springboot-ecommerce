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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 45, nullable = false, unique = true)
    private String name;
    
    @Column(length = 128)
    private String logo;
    
    @ManyToMany
    @JoinTable(name = "brands_categories", 
    	       joinColumns = @JoinColumn(name = "brand_id"),
    	       inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Brand() {}
    
    public Brand(Integer id) {
	this.id = id;
    }

    public Brand(String name) {
	this.name = name;
    }
    
    public Brand(Integer id, String name) {
	this.id = id;
	this.name = name;
    }

    public Brand(String name, String logo) {
	this.name = name;
	this.logo = logo;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(Category category) {
	this.categories.add(category);
    }
    
    public String getLogoPathString() {
	if (id == null || logo == null) return Constants.S3_BASE_URI + "/brand-logos/default-logo.png";
	return Constants.S3_BASE_URI + "/brand-logos/" + this.getId() + "/" + this.getLogo();
    }

    @Override
    public String toString() {
	return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + ", categories=" + categories + "]";
    }
 }
