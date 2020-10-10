package com.yang.jpalearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
@ComponentScan("com.yang.jpalearning.*")
public class JpaLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaLearningApplication.class, args);
    }

}
