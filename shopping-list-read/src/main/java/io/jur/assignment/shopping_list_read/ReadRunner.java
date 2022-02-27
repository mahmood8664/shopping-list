package io.jur.assignment.shopping_list_read;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReadRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReadRunner.class, args);
    }

}
