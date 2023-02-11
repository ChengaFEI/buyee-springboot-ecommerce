package com.chengfei.buyee.admin;

//import java.nio.file.Path;
//import java.nio.file.Paths;

//import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	enableFolderAccess("user-photos", registry);
//	enableFolderAccess("category-images", registry);
//	enableFolderAccess("brand-logos", registry);
//	enableFolderAccess("product-images", registry);
    }
    
//    private void enableFolderAccess(String folderName, ResourceHandlerRegistry registry) {
//	Path folderPath = Paths.get(folderName);
//	String absFolderName = folderPath.toFile().getAbsolutePath();
//	registry.addResourceHandler("/" + folderName + "/**").addResourceLocations("file:" + absFolderName + "/");
//    }
}
