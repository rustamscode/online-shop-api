package rustamscode.onlineshopapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rustamscode.onlineshopapi.dto.ProductRequest;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/filter")
    public List<Product> getFilteredAndSortedProducts(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) Double minPrice,
                                                      @RequestParam(required = false) Double maxPrice,
                                                      @RequestParam(required = false, defaultValue = "true") Boolean available,
                                                      @RequestParam(required = false, defaultValue = "name") String sortBy,
                                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection,
                                                      @RequestParam(required = false, defaultValue = "5") Integer limit
    ) {
        return productService.getFilteredAndSortedProducts(name, minPrice, maxPrice, available, sortBy, sortDirection, limit);
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    @PutMapping("update/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequest product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
