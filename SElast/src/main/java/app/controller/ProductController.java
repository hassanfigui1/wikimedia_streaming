package app.controller;

import app.payload.Product;
import app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findAll")
    public String findAll(@RequestParam(defaultValue = "0") int page, Model model) {
        logger.info("findAll");
        int pageSize = 10;
        model.addAttribute("products", productService.getAllProducts(page, pageSize).getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productService.getTotalPages(pageSize));
        return "list";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable String id, Model model) {
        logger.info("getProductById");
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "products/detail";
    }

    @GetMapping("/newProduct")
    public String showCreateForm(Model model) {
        logger.info("showCreateForm");
        Product product = new Product().builder()
                .name(" ")
                .price(1.0)
                .description(" ")
                .quantity(1)
                .build();
        model.addAttribute("product",product) ;
        return "products/form";
    }

    @PostMapping("/newProduct")
    public String createProduct(@ModelAttribute Product product) {
        logger.info("createProduct");
        try {
            productService.insertProduct(product);
            return "redirect:/products/findAll";
        } catch (Exception e) {
            logger.error("Error msg is : {}", e.getMessage());
            return "products/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        logger.info("showEditForm");
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "products/form";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable String id, @ModelAttribute Product product) {
        logger.info("updateProduct");
        productService.updateProduct(product, id);
        return "redirect:/products/findAll";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "redirect:/products/findAll";
    }
}