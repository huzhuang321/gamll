package com.gmall.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GmallRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallRegistryApplication.class, args);
    }
}
