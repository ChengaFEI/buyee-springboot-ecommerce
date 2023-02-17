package com.chengfei.buyee.common.exception;
public class ProductNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    public ProductNotFoundException(String message) {
	super(message);
    }
}
