package com.cn.shop_sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cn")
public class ShopSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSsoApplication.class, args);
    }

}
