package io.jur.assignment.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mahmood
 * @since 9/14/21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayRunner {

    public static void main(String[] args) {
        SpringApplication.run(GatewayRunner.class, args);
    }
}
