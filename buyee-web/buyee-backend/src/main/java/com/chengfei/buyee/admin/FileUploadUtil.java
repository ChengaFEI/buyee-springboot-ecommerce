package com.chengfei.buyee.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
    
    public static void saveFile(String folderName, String fileName, MultipartFile multipartFile) throws IOException {
	Path folderPath = Paths.get(folderName);
	if (!Files.exists(folderPath))
	    Files.createDirectories(folderPath);
	try (InputStream inputStream = multipartFile.getInputStream()) {
	    Path filePath = folderPath.resolve(fileName);
	    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
	    LOGGER.error("Could not save file: " + fileName);
	}
    }
    
    public static void cleanFolder(String folderName) {
	Path folderPath = Paths.get(folderName);
	try {
	    Files.list(folderPath).forEach((file) -> {
		if (!Files.isDirectory(file)) {
		    try {
			Files.delete(file);
		    } catch (IOException e) {
			LOGGER.error("Could not delete file: " + file);
		    }
		}
	    }); 
	} catch (IOException e) {
	    LOGGER.error("Could not list all files in the directory: " + folderPath);
	}
    }
    
    public static void removeFolder(String folderName) {
	cleanFolder(folderName);
	try {
	    Files.delete(Paths.get(folderName));
	} catch (IOException e) {
	    LOGGER.error("Could not delete the directory: " + folderName);
	}
    }
}
