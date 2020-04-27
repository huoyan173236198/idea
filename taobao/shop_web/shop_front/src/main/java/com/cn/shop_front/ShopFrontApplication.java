package com.cn.shop_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cn")
public class ShopFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFrontApplication.class, args);
    }

}
