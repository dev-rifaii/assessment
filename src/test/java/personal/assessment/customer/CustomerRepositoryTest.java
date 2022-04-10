package personal.assessment.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import personal.assessment.MySqlTestContainerExtension;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MySqlTestContainerExtension.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private final Customer validCustomer =
            Customer.builder()
                    .name("Test")
                    .email("test@test.co")
                    .phoneNumber("15442255")
                    .build();

    @Test
    void given_valid_customer_should_persist() {
        customerRepository.save(validCustomer);
        Customer customer = customerRepository.findByEmail(validCustomer.getEmail());
        assertNotNull(customer);
        assertTrue(customerRepository.existsByEmail(validCustomer.getEmail()));
    }

    @Test
    void given_existing_customer_email_should_return_true() {
        assertTrue(customerRepository.existsByEmail(validCustomer.getEmail()));
    }


}