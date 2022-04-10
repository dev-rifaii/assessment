package personal.assessment.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import personal.assessment.exception.ProductExistsException;


import javax.validation.Valid;

@Service
@AllArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;


    public void create(@Valid Product product) {
        if (productExists(product)) {
            throw new ProductExistsException("Product already exists");
        }
        productRepository.save(product);
    }

    public boolean productExists(Product product) {
        if (productRepository.existsById(product.getSkuCode()) || productRepository.existsByName(product.getName())) {
            return true;
        }
        return false;
    }
}
