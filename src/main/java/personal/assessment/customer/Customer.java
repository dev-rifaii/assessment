package personal.assessment.customer;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import personal.assessment.Constants;
import personal.assessment.order.Order;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String registrationCode;
    @NotBlank(message = "Name can't be blank")
    private String name;
    @Pattern(regexp = Constants.EMAIL_REGEX, message = "Invalid email")
    private String email;
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX, message = "Invalid number")
    private String phoneNumber;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private List<Order> orders;
}
