package com.chengfei.buyee.admin.product;
public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public ProductNotFoundException(String message) {
	super(message);
    }
}
