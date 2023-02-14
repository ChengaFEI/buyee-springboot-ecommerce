package com.chengfei.buyee.admin.product.controller;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.chengfei.buyee.admin.AmazonS3Util;
import com.chengfei.buyee.common.entity.Product;
import com.chengfei.buyee.common.entity.ProductImage;
public class ProductControllerSaveUtil {
    // Save Images in Database
    static void setMainImageName(Product product, MultipartFile mainImageMultipart) {
	if (!mainImageMultipart.isEmpty()) {
	    String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
	    product.setMainImage(fileName);
	}
    }
    static void setExistingExtraImageNames(Product product, String[] existingExtraImageIds,
	    String[] existingExtraImageNames) {
	if (existingExtraImageIds == null || existingExtraImageIds.length == 0) return;
	Set<ProductImage> images = new HashSet<>();
	for (int i = 0; i < existingExtraImageIds.length; i++) {
	    Integer id = Integer.parseInt(existingExtraImageIds[i]);
	    String name = existingExtraImageNames[i];
	    images.add(new ProductImage(id, name, product));
	}
	product.setImages(images);
    }
    static void setNewExtraImageNames(Product product, MultipartFile[] extraImageMultiparts) {
	if (extraImageMultiparts != null && extraImageMultiparts.length > 0) {
	    for (MultipartFile multipartFile: extraImageMultiparts) {
		if (!multipartFile.isEmpty()) {
		    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		    if (!product.containExtraImageName(fileName))
			product.addExtraImage(fileName);
		}
	    }
	}
    }
    // Save Images in Amazon S3
    static void uploadImages(Product product, MultipartFile mainImageMultipart,
	    MultipartFile[] extraImageMultiparts) throws IOException {
	if (!mainImageMultipart.isEmpty()) {
	    String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
	    String folderName = "product-images/" + product.getId();
	    // Remove existing main image in the product's folder
	    List<String> listObjectKeys = AmazonS3Util.listFolder(folderName);
	    for (String objectKey : listObjectKeys) {
		if (!objectKey.contains("/extras"))
		    AmazonS3Util.deleteFile(objectKey);
	    }
	    // Upload new main image to the product's folder
	    AmazonS3Util.saveFile(folderName, fileName, mainImageMultipart.getInputStream());
	}
	if (extraImageMultiparts.length > 0) {
	    String extraFolderName = "product-images/" + product.getId() + "/extras";    
	    for (MultipartFile multipartFile: extraImageMultiparts) {
		if (!multipartFile.isEmpty()) {
		    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		    AmazonS3Util.saveFile(extraFolderName, fileName, multipartFile.getInputStream());
		}
	    }
	}
    }
    // Delete orphan images (images removed from the form) in Amazon S3
    static void deleteRemovedExtraImages(Product product) {
	String extraImageFolderName = "product-images/" + product.getId() + "/extras";
	List<String> listObjectKeys = AmazonS3Util.listFolder(extraImageFolderName);
	for (String objectKey : listObjectKeys) {
	    int indexOfLastSlash = objectKey.lastIndexOf("/");
	    String fileName = objectKey.substring(indexOfLastSlash + 1, objectKey.length());
	    if (!product.containExtraImageName(fileName))
		AmazonS3Util.deleteFile(objectKey);
	}
    }
    // Save Details in Database
    static void setDetails(Product product, String[] detailIds, String[] detailNames, String[] detailValues) {
	if (detailNames == null || detailNames.length == 0) return;
	for (int i = 0; i < detailIds.length; i++) {
	    Integer id = Integer.parseInt(detailIds[i]);
	    String name = detailNames[i];
	    String value = detailValues[i];
	    if (id != null && id != 0) {
		product.addDetail(id, name, value);
	    } else if (!name.isEmpty() && !value.isEmpty()) {
		product.addDetail(name, value);
	    }
	}
    }
}
