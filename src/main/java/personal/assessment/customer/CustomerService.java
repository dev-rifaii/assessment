package personal.assessment.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import personal.assessment.exception.CustomerExistsException;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class CustomerService {


    private final CustomerRepository customerRepository;

    public void create(@Valid Customer customer) {
        if (!customerExists(customer)) {
            customerRepository.save(customer);
        }
    }

    public boolean customerExists(Customer customer) {
        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber()) || customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomerExistsException("Customer already exists");
        }
        return false;
    }

}
