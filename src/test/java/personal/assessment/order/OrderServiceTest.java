package personal.assessment.order;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.assessment.MySqlTestContainerExtension;
import personal.assessment.customer.Customer;
import personal.assessment.customer.CustomerRepository;
import personal.assessment.exception.InvalidDateFormatException;
import personal.assessment.exception.NonExistentCustomerException;
import personal.assessment.orderline.OrderLine;
import personal.assessment.product.Product;
import personal.assessment.product.ProductRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MySqlTestContainerExtension.class)
@SpringBootTest
class OrderServiceTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;
    private final Customer validCustomer =
            Customer.builder()
                    .name("ordercustomer")
                    .email("order@test.com")
                    .phoneNumber("12356789")
                    .build();
    private final Product validProduct = Product.builder()
            .name("Milk")
            .price(1L)
            .skuCode("ML123")
            .build();


    @Test
    void given_order_with_valid_customer_and_product_should_persist() {
        insertProduct(validProduct);
        insertCustomer(validCustomer);
        Order order = setupOrder(customerRepository.findByEmail(validCustomer.getEmail()), productRepository.getById(validProduct.getSkuCode()), 1);
        orderService.create(order);
        List<Order> orders = orderRepository.findByDateOfSubmission(LocalDate.of(2022, 1, 1));
        assertTrue(orders.size() == 1);
    }

    @Test
    void given_order_with_non_existing_customer_should_throw_exception() {
        insertProduct(validProduct);
        Customer customer = Customer.builder()
                .registrationCode("test")
                .build();
        Order order = setupOrder(customer, validProduct, 1);
        assertThrows(NonExistentCustomerException.class, () -> orderService.create(order));
    }


    @Test
    void given_order_with_negative_product_quantity_should_throw_exception() {
        insertCustomer(validCustomer);
        insertProduct(validProduct);
        Order order = setupOrder(validCustomer, validProduct, -1);
        assertThrows(IllegalArgumentException.class, () -> orderService.create(order));
    }

    @Test
    void given_invalid_date_should_throw_exception() {
        assertThrows(InvalidDateFormatException.class, () -> orderService.findOrdersByDate("20-1-1"));
    }

    void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    void insertProduct(Product product) {
        productRepository.save(product);
    }

    Order setupOrder(Customer customer, Product product, Integer productQuantity) {
        OrderLine orderLine = OrderLine.builder()
                .product(product)
                .quantity(productQuantity)
                .build();
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        Order order = Order.builder()
                .customer(customer)
                .orderLines(orderLines)
                .dateOfSubmission(LocalDate.of(2022, 1, 1))
                .build();
        return order;
    }
}