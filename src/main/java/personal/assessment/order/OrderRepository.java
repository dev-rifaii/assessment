package personal.assessment.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import personal.assessment.customer.Customer;
import personal.assessment.product.Product;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByDateOfSubmission(LocalDate dateOfSubmission);

    //Criteria query
    List<Order> findByCustomer(Customer customer);

    //Criteria query
    List<Order> findByOrderLinesProduct(Product product);


    @Query("SELECT o FROM Order o WHERE o.customer = ?1 ")
    List<Order> findByCustomerJPQL(Customer customer);

    @Query("SELECT o FROM Order o JOIN o.orderLines r WHERE r.product=?1")
    List<Order> findByOrderLinesProductJPQL(Product product);
}
