package io.jur.assignment.shopping_list.write.integration;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(initializers = BaseTest.Initializer.class)
@AutoConfigureMockMvc
public class BaseTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:6-alpine")
            .withExposedPorts(6379)
            .withReuse(true);

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:9.6.24")
            .withUsername("postgres")
            .withPassword("postgres")
            .withReuse(true);

    static {
        redis.start();
        postgreSQLContainer.start();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.hikari.jdbc-url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.hikari.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.hikari.password=" + postgreSQLContainer.getPassword(),
                    "spring.redis.host=" + redis.getHost(),
                    "spring.redis.port=" + redis.getMappedPort(6379)
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
