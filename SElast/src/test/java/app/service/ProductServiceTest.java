package app.service;

import app.payload.Product;
import app.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.apache.kafka.common.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("1", "Product A","jfkdf fdkjf", 100.0,55);
    }

    @Test
    void testUpdateProduct() {
        when(productRepo.findById("1")).thenReturn(Optional.of(product));
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = new Product("1", "Updated Product A","djskf fdkjf", 120.0,25);
        Product result = productService.updateProduct(updatedProduct, "1");

        assertEquals("Updated Product A", result.getName());
        assertEquals(120.0, result.getPrice());
        verify(productRepo).save(updatedProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepo.findById("1")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(new Product("1KLDKLSD", "Product A","New fhjdfh fdjfh", 100.0,52), "1");
        });

        assertEquals("Product not found with id 1", thrown.getMessage());
    }

    @Test
    void testGetProduct() {
        when(productRepo.findById("1")).thenReturn(Optional.of(product));

        Product result = productService.getProduct("1");

        assertEquals("Product A", result.getName());
        assertEquals(100.0, result.getPrice());
    }

    @Test
    void testGetProductNotFound() {
        when(productRepo.findById("1")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProduct("1");
        });

        assertEquals("Product not found with id 1", thrown.getMessage());
    }

    @Test
    void testDeleteProduct() {
        when(productRepo.findById("1")).thenReturn(Optional.of(product));

        productService.deleteProduct("1");

        verify(productRepo).deleteById("1");
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepo.findById("1")).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct("1");
        });

        assertEquals("Product not found with id 1", thrown.getMessage());
    }

    @Test
    void testGetAllProducts() {
        when(productRepo.findAll()).thenReturn(List.of(product));

        Iterable<Product> result = productService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
    }

    @Test
    void testInsertProduct() {
        when(productRepo.save(any(Product.class))).thenReturn(product);

        Product result = productService.insertProduct(product);

        assertEquals("Product A", result.getName());
        assertEquals(100.0, result.getPrice());
        verify(productRepo).save(product);
    }
}
