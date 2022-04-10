package personal.assessment.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.assessment.MySqlTestContainerExtension;
import personal.assessment.exception.ProductExistsException;


import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(MySqlTestContainerExtension.class)
@SpringBootTest
class ProductServiceTest {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private final Product validProduct =
            Product.builder()
                    .name("Milk")
                    .price(1L)
                    .skuCode("ML123")
                    .build();

    @Test
    void given_valid_product_should_persist() {
        productService.create(validProduct);
        assertTrue(productRepository.existsByName(validProduct.getName()));
    }


    @Test
    void given_existing_product_should_throw_exception() {
        productService.create(validProduct);
        Product product = Product.builder()
                .skuCode(validProduct.getSkuCode())
                .name("Test")
                .price(1L)
                .build();
        assertThrows(ProductExistsException.class, () -> productService.create(product));
    }

}