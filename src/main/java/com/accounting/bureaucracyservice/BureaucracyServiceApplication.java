package com.accounting.bureaucracyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BureaucracyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BureaucracyServiceApplication.class, args);
    }

}
