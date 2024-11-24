package rustamscode.onlineshopapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnAllProducts() {
        Product product1 = new Product(1L, "Product1", "Description1", BigDecimal.valueOf(100), true);
        Product product2 = new Product(2L, "Product2", "Description2", BigDecimal.valueOf(200), false);

        productService.createProduct(product1);
        productService.createProduct(product2);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void shouldThrowExceptionIfProductNotFound() {
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(999L);
        });

        assertEquals("Продукт с ID 999 не найден", exception.getMessage());
    }
}
