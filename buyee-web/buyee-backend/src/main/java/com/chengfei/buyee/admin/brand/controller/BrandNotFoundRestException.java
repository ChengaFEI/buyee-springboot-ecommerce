package com.chengfei.buyee.admin.brand.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Brand Not Found")
public class BrandNotFoundRestException extends Exception {

    private static final long serialVersionUID = 1L;

}
