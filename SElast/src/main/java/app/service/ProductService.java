package app.service;

import app.payload.Product;
import app.repository.ProductRepo;
import app.service.reflect.EntityUpdater;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepo productRepo;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    public Iterable<Product> getAllProducts() {
        LOGGER.info("Getting all products");
        return  this.productRepo.findAll();
    }
    public Product insertProduct(Product product) {
        LOGGER.info("Inserting product {}", product);
        return this.productRepo.save(product);
    }
    @Transactional
    public Product updateProduct(Product product, long id ) {
        LOGGER.info("Updating product with id: {} ", id);
        return this.productRepo.findById(id)
                .map(existingProduct->{
                    existingProduct = EntityUpdater.updateEntity(existingProduct, product);
                    return this.productRepo.save(existingProduct);
                }).orElseThrow(()->new ResourceNotFoundException("Product not found with id "+id));
    }
    public Product getProduct(long id) {
        return this.productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id "+id));
    }

    @Transactional
    public void deleteProduct(long id) {
        LOGGER.info("Deleting product with id {}", id);
        this.productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id "+id));
        this.productRepo.deleteById(id);
    }
}
