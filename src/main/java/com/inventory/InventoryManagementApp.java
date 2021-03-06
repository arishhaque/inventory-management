package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.inventory"})
@SpringBootApplication
public class InventoryManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApp.class, args);
    }
}
