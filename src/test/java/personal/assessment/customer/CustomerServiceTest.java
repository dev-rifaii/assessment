package personal.assessment.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.assessment.MySqlTestContainerExtension;
import personal.assessment.exception.CustomerExistsException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@Transactional
@ExtendWith(MySqlTestContainerExtension.class)
@SpringBootTest
class CustomerServiceTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    private final Customer validCustomer =
            Customer.builder()
                    .name("ordercustomer")
                    .email("order@test.com")
                    .phoneNumber("12356789")
                    .build();


    @Test
    void given_valid_customer_should_persist() {
        customerService.create(validCustomer);
        assertTrue(customerRepository.existsByEmail(validCustomer.getEmail()));
    }

    @Test
    void given_existing_customer_should_throw_exception() {
        customerService.create(validCustomer);
        Customer customer = Customer.builder()
                .name("Test")
                .email(validCustomer.getEmail())
                .phoneNumber("12345678")
                .build();
        assertThrows(CustomerExistsException.class, () -> customerService.create(customer));
    }
}