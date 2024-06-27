package com.accounting.bureaucracyservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        servers = {
                @Server(
                        url = "/bureaucracy",
                        description = "Gateway Server Url"
                )
        },
        security = @SecurityRequirement(name = "Authorization")
)
public class BureaucracyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BureaucracyServiceApplication.class, args);
    }

}
