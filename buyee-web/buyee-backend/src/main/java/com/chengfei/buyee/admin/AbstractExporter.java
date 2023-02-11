package com.chengfei.buyee.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletResponse;

public class AbstractExporter {
    public void setResponseHeader(HttpServletResponse response, String contentType, 
	    			  String prefix, String extension) {
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	String dateStamp = formatter.format(new Date());
	String fileName = prefix + dateStamp + extension;
	response.setContentType(contentType);
	String headerKey = "Content-Disposition";
	String headerValue = "attachement; fileName=" + fileName;
	response.setHeader(headerKey, headerValue);
    }
}
