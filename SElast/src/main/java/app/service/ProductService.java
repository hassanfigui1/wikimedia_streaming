package app.service;

import app.payload.Product;
import app.repository.ProductRepo;
import app.service.reflect.EntityUpdater;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepo productRepo;

    private static final String PRODUCT_NOT_FOUND_MSG = "Product not found with id %s";

    public Iterable<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

    public Product insertProduct(Product product) {
        return this.productRepo.save(product);
    }

    @Transactional
    public Product updateProduct(Product product, String id) {
        return this.productRepo.findById(id)
                .map(existingProduct -> {
                    Product updatedProd = EntityUpdater.updateEntity(existingProduct, product);
                    return this.productRepo.save(updatedProd);
                }).orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, id)));
    }

    public Product getProduct(String id) {
        return this.productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, id)));
    }

    @Transactional
    public void deleteProduct(String id) {
        productRepo.findById(id).ifPresentOrElse(product -> {
            productRepo.deleteById(id);
        }, () -> {
            throw new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, id));
        });
    }
}
