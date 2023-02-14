package com.chengfei.buyee.common.entity;
import com.chengfei.buyee.common.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    // Constructors
    public ProductImage() {}
    public ProductImage(String name, Product product) {
	this.name = name;
	this.product = product;
    }
    public ProductImage(Integer id, String name, Product product) {
	this.id = id;
	this.name = name;
	this.product = product;
    }
    // Getters and Setters
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
    @Transient
    public String getImagePathString() {
	if (id == null || name == null) return Constants.S3_BASE_URI + "/product-images/default-image.png";
	return Constants.S3_BASE_URI + "/product-images/" + this.getProduct().getId() + "/extras/" + this.name;
    }
    @Transient
    public String getShortImageName() {
	return getImageNameWithChars(20);
    }
    @Transient
    public String getImageNameWithChars(int numChars) {
	if (this.name.length() <= numChars) return this.name;
	return this.name.substring(0, numChars).concat("...");
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
