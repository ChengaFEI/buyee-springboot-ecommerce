package com.chengfei.buyee.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.chengfei.buyee.common.Constants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 256, nullable = false, unique = true)
    private String name;
    @Column(length = 256, nullable = false, unique = true)
    private String alias;
    @Column(name = "short_description",length = 512, nullable = false)
    private String shortDescription;
    @Column(name = "full_description", length = 4096, nullable = false)
    private String fullDescription;
    
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "updated_time")
    private Date updatedTime;
    
    private boolean enabled;
    @Column(name = "in_stock")
    private boolean inStock;
    
    private float cost;
    private float price;
    @Column(name = "discount_percent")
    private float discountPercent;

    private float length;
    private float width;
    private float height;
    private float weight;
    
    @Column(name = "main_image")
    private String mainImage;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductImage> images = new HashSet<>();
    
    public Product() {}
     
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
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getshortDescription() {
        return shortDescription;
    }
    public void setshortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
    
    public Date getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    public Date getUpdatedTime() {
        return updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isInStock() {
        return inStock;
    }
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }
    public float getLength() {
        return length;
    }
    public void setLength(float length) {
        this.length = length;
    }
    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    public String getMainImage() {
        return mainImage;
    }
    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Brand getBrand() {
        return brand;
    }
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
    public Set<ProductImage> getImages() {
        return images;
    }
    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }
    public void addExtraImage(String imageName) {
	this.images.add(new ProductImage(imageName, this)); 
    }
    public String getMainImagePathString() {
	if (id == null || mainImage == null) return Constants.S3_BASE_URI + "/product-images/default-image.png";
	return Constants.S3_BASE_URI + "/product-images/" + this.getId() + "/" + this.getMainImage();
    }

    @Override
    public String toString() {
	return "Product [id=" + id + ", name=" + name + "]";
    }
    
    private Product(ProductBuilder builder) {
	this.id = builder.id;
	this.name = builder.name;
	this.alias = builder.alias;
	this.shortDescription = builder.shortDescription;
	this.fullDescription = builder.fullDescription;
	this.createdTime = builder.createdTime;
	this.updatedTime = builder.updatedTime;
	this.enabled = builder.enabled;
	this.inStock = builder.inStock;
	this.cost = builder.cost;
	this.price = builder.price;
	this.discountPercent = builder.discountPercent;
	this.length = builder.length;
	this.width = builder.width;
	this.height = builder.height;
	this.weight = builder.weight;
	this.category = builder.category;
	this.brand = builder.brand;
    }
    
    public static class ProductBuilder {
	 private Integer id;
	 private String name;
	 private String alias;
	 private String shortDescription;
	 private String fullDescription;
	 private Date createdTime;
	 private Date updatedTime;
	 private boolean enabled;
	 private boolean inStock;
	 private float cost;
	 private float price;
	 private float discountPercent;
	 private float length;
	 private float width;
	 private float height;
	 private float weight;
	 private Category category;
	 private Brand brand;
	 
	 public ProductBuilder(String name, String alias, String shortDescription, String fullDescription) {
	     this.name = name;
	     this.alias = alias;
	     this.shortDescription = shortDescription; 
	     this.fullDescription = fullDescription; 
	 }
	 
	 public ProductBuilder setId(Integer id) {
	     this.id = id;
	     return this;
	 }
	 public ProductBuilder setCreatedTime(Date createdTime) {
	     this.createdTime = createdTime;
	     return this;
	 }
	 public ProductBuilder setUpdatedTime(Date updatedTime) {
	     this.updatedTime = updatedTime;
	     return this;
	 }
	 public ProductBuilder setEnabled(boolean enabled) {
	     this.enabled = enabled;
	     return this;
	 }
	 public ProductBuilder setInStock(boolean inStock) {
	     this.inStock = inStock;
	     return this;
	 }
	 public ProductBuilder setCost(float cost) {
	     this.cost = cost;
	     return this;
	 }
	 public ProductBuilder setPrice(float price) {
	     this.price = price;
	     return this;
	 }
	 public ProductBuilder setDiscountPercent(float discountPercent) {
	     this.discountPercent = discountPercent;
	     return this;
	 }
	 public ProductBuilder setLength(float length) {
	     this.length = length;
	     return this;
	 }
	 public ProductBuilder setWidth(float width) {
	     this.width = width;
	     return this;
	 }
	 public ProductBuilder setHeight(float height) {
	     this.height = height;
	     return this;
	 }
	 public ProductBuilder setWeight(float weight) {
	     this.weight = weight;
	     return this;
	 }
	 public ProductBuilder setBrand(Brand brand) {
	     this.brand = brand;
	     return this;
	 }
	 public ProductBuilder setCategory(Category category) {
	     this.category = category;
	     return this;
	 }
	 public Product build() {
	     return new Product(this);
	 }
    }
}