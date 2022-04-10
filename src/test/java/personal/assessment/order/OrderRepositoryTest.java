package personal.assessment.order;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.assessment.MySqlTestContainerExtension;
import personal.assessment.customer.Customer;
import personal.assessment.customer.CustomerRepository;
import personal.assessment.orderline.OrderLine;
import personal.assessment.product.Product;
import personal.assessment.product.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


@Transactional
@ExtendWith(MySqlTestContainerExtension.class)
class OrderRepositoryTest  {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private final Customer validCustomer =
            Customer.builder()
                    .name("ordercustomer")
                    .email("order@test.com")
                    .phoneNumber("12356789")
                    .build();

    private final Product validProduct =
            Product.builder()
                    .name("Milk")
                    .price(1L)
                    .skuCode("ML123")
                    .build();

    @Test
    void given_date_should_return_order() {
        insertOrder();
        List<Order> orders = orderRepository.findByDateOfSubmission(LocalDate.of(2022, 1, 1));
        assertEquals(1, orders.size());
    }


    @Test
    void given_customer_should_return_order() {
        insertOrder();
        Customer customer = customerRepository.findByEmail(validCustomer.getEmail());
        List<Order> orders = orderRepository.findByCustomer(customer);
        assertTrue(orders.size() == 1);
    }


    @Test
    void given_product_should_return_order() {
        insertOrder();
        Product product = productRepository.getById(validProduct.getSkuCode());
        List<Order> orders = orderRepository.findByOrderLinesProduct(product);
        assertTrue(orders.size() == 1);
    }


    void insertOrder() {
        customerRepository.save(validCustomer);
        productRepository.save(validProduct);
        OrderLine orderLine = OrderLine.builder()
                .product(productRepository.getById(validProduct.getSkuCode()))
                .quantity(1)
                .build();
        List<OrderLine> orderLines = new ArrayList<>();
        orderLines.add(orderLine);
        Order order = Order.builder()
                .customer(customerRepository.findByEmail(validCustomer.getEmail()))
                .orderLines(orderLines)
                .dateOfSubmission(LocalDate.of(2022, 1, 1))
                .build();
        orderRepository.save(order);
    }
}