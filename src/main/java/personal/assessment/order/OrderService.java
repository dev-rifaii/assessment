package personal.assessment.order;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import personal.assessment.Constants;
import personal.assessment.customer.Customer;
import personal.assessment.customer.CustomerRepository;
import personal.assessment.exception.InvalidDateFormatException;
import personal.assessment.exception.NonExistentCustomerException;
import personal.assessment.exception.NonExistentProductException;
import personal.assessment.orderline.OrderLine;
import personal.assessment.product.ProductRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public void create(@Valid Order order) {
        validateOrderLines(order.getOrderLines());
        validateCustomer(order.getCustomer());
        orderRepository.save(order);
    }

    public List<Order> findOrdersByDate(String date) {
        if (date.matches(Constants.DATE_REGEX)) {
            LocalDate localDate = LocalDate.parse(date);
            return orderRepository.findByDateOfSubmission(localDate);
        }
        throw new InvalidDateFormatException("Invalid date format, please use yyyy-mm-dd");
    }

    public boolean validateCustomer(Customer customer) {
        if (customer == null || !customerRepository.existsByEmail(customer.getEmail())) {
            throw new NonExistentCustomerException("Customer does not exist");
        }
        return true;
    }

    public boolean validateOrderLines(List<OrderLine> orderLines) {
        orderLines.forEach(orderLine -> {
            if (productRepository.getById(orderLine.getProduct().getSkuCode()) == null) {
                throw new NonExistentProductException("Product does not exist");
            }
            if (orderLine.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity");
            }
        });
        return true;
    }
}
