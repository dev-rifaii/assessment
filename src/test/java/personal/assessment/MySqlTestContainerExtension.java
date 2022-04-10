package personal.assessment;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
public  class MySqlTestContainerExtension implements BeforeAllCallback {
    private static MySQLContainer container = (MySQLContainer) new MySQLContainer("mysql:latest").withReuse(true);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

//    static {
//        if (!container.isRunning()) {
//            container.start();
//        }
//    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!container.isRunning()) {
            container.start();
        }
    }
}
