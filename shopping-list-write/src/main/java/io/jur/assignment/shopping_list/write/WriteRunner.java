package io.jur.assignment.shopping_list.write;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WriteRunner {

    public static void main(String[] args) {
        SpringApplication.run(WriteRunner.class, args);
    }

}
