package personal.assessment.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @NotBlank(message = "Product ID can't be empty")
    private String skuCode;
    @NotBlank(message = "Invalid name")
    private String name;
    @NotNull
    @Min(value = 0, message = "Price should be positive")
    private Long price;

}
