package com.chengfei.buyee.admin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@SpringBootApplication
@EntityScan({"com.chengfei.buyee.common.entity", "com.chengfei.buyee.admin.user"})
public class BuyeeBackendApplication {
    public static void main(String[] args) {
	SpringApplication.run(BuyeeBackendApplication.class, args);
    }
}
