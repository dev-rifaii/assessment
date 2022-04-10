package personal.assessment.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.assessment.MySqlTestContainerExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@Transactional

@ExtendWith(MySqlTestContainerExtension.class)
class ProductRepositoryTest  {

    @Autowired
    private ProductRepository productRepository;


    private final Product validProduct =
            Product.builder()
                    .name("Milk")
                    .price(1L)
                    .skuCode("ML123")
                    .build();


    @Test
    void given_existing_product_should_return_true() {
        productRepository.save(validProduct);
        assertTrue(productRepository.existsByName(validProduct.getName()));

    }

    @Test
    void given_non_existing_product_should_return_false() {
        assertFalse(productRepository.existsByName("?"));
    }

}