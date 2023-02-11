package com.chengfei.buyee.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AmazonS3UtilTests {
    // Create Tasks
    
    @Test
    public void testSaveFile() throws FileNotFoundException {
	String folderName = "test-upload";
	String fileName = "Cameras.png";
	String filePath = "/Users/chengfei/Downloads/images/" + fileName;
	InputStream inputStream = new FileInputStream(filePath);
	AmazonS3Util.saveFile(folderName, fileName, inputStream);
    }
    
    // Read Tasks
    
    @Test
    public void testListFolder() {
	String folderName = "user-photos/1";
	List<String> listKeys = AmazonS3Util.listFolder(folderName);
	listKeys.forEach(System.out::println);
    }
    
    // Delete Tasks
    
    @Test
    public void testDeleteFile() {
	 String fileName = "test-upload/Cameras.png";
	 AmazonS3Util.deleteFile(fileName);
    }
    
    @Test 
    public void testDeleteFolder() {
	String folderName = "user-photos";
	AmazonS3Util.deleteFolder(folderName);
    }
}
