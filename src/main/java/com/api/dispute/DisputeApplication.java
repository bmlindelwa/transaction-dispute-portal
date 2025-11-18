package com.api.dispute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DisputeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DisputeApplication.class, args);
        //System.out.println(new BCryptPasswordEncoder().encode("password"));
    }
}
