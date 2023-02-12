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
    
    public ProductImage() {}
    public ProductImage(String name, Product product) {
	this.name = name;
	this.product = product;
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
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    @Transient
    public String getImagePathString() {
	if (id == null || name == null) return Constants.S3_BASE_URI + "/product-images/default-image.png";
	return Constants.S3_BASE_URI + "/product-images/" + this.getProduct().getId() + "/extras/" + this.name;
    }
}
