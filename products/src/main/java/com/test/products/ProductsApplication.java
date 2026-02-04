package com.test.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	 @GetMapping("/hello")
    public String hello() {
        return "Hello World from Spring Boot!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
