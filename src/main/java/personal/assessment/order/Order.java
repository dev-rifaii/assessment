package personal.assessment.order;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import personal.assessment.customer.Customer;
import personal.assessment.orderline.OrderLine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//"order" is reserved by mySQL
@Table(name = "\"order\"")
public class Order {


    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_code")
    @NotNull
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private List<OrderLine> orderLines;
    @Column(name = "date_of_submission", columnDefinition = "DATE")
    private LocalDate dateOfSubmission;

}
