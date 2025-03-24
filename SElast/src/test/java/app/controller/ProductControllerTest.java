package app.controller;

import app.payload.Product;
import app.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product("1", "Product A", "Description", 100.0, 10);
    }

    @Test
    void testFindAll() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        mockMvc.perform(get("/products/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product A"))
                .andExpect(jsonPath("$[0].price").value(100.0));

        verify(productService).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProduct(anyString())).thenReturn(product);

        mockMvc.perform(get("/products/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product A"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService).getProduct("1");
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.insertProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products/newProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Product A\",\"description\":\"Description\",\"price\":100.0,\"quantity\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product A"))
                .andExpect(jsonPath("$.price").value(100.0));

        verify(productService).insertProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product("1", "Updated Product", "Updated Description", 120.0, 20);

        when(productService.updateProduct(any(Product.class), anyString())).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/update/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"Updated Product\",\"description\":\"Updated Description\",\"price\":120.0,\"quantity\":20}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(120.0));

        verify(productService).updateProduct(any(Product.class), eq("1"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyString());

        mockMvc.perform(delete("/products/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct("1");
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        doThrow(new RuntimeException("Product not found")).when(productService).deleteProduct(anyString());

        mockMvc.perform(delete("/products/{id}", "1"))
                .andExpect(status().isInternalServerError());

        verify(productService).deleteProduct("1");
    }
}
