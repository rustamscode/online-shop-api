package rustamscode.onlineshopapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rustamscode.onlineshopapi.dto.ProductRequest;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        Product product1 = new Product();
        product1.setName("Product A");
        product1.setInfo("Description A");
        product1.setPrice(BigDecimal.valueOf(441));
        product1.setAvailable(true);

        Product product2 = new Product();
        product2.setName("Product B");
        product2.setInfo("Description B");
        product2.setPrice(BigDecimal.valueOf(9324));
        product2.setAvailable(false);

        Product product3 = new Product();
        product3.setName("Product C");
        product3.setInfo("Description C");
        product3.setPrice(BigDecimal.valueOf(300));
        product3.setAvailable(true);

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @Test
    @DisplayName("Тест проверяет функицонал получения всех продуктов")
    void shouldReturnAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(3, products.size());
        assertEquals("Product C", products.get(2).getName());
    }

    @Test
    @DisplayName("Тест проверяет функционал выброса исключений при попытке получить продукт, ID которого нет в БД")
    void shouldThrowExceptionWhenProductNotFound() {
        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(1L));
        assertEquals("Продукт с ID 1 не найден", exception.getMessage());
    }

    @Test
    @DisplayName("Тест на проверку создания нового продукта и добавления его в БД")
    void shouldCreateProduct() {
        ProductRequest productRequest = new ProductRequest("Product D", "Description D", 200);

        Product result = productService.createProduct(productRequest);

        assertNotNull(result);
        assertEquals("Product D", result.getName());
    }

    @Test
    @DisplayName("Тест проверяет выброс иселючение при попытке удаления несуществуещего продукта")
    void shouldThrowExceptionWhenDeletingNonexistentProduct() {
        Exception exception = assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProduct(1L));
        assertEquals("Продукт с ID 1 не найден", exception.getMessage());
    }


    @Test
    @DisplayName("Проверка валидации на длинну имени продукта (максимальное значение = 255)")
    public void testGetFilteredAndSortedProductsNameTooLong() {
        String name = "name".repeat(256);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                productService.getFilteredAndSortedProducts(name, null, null, true, "name", "asc", 5));
        assertEquals("Название товара слишком длинное", exception.getMessage());
    }

    @Test
    @DisplayName("Проверка применения фильтра при получении продуктов")
    void testFilteredProducts() {
        List<Product> filtered = productService.getFilteredAndSortedProducts(null, null, null, true, "name", "asc", 5);
        assertEquals(2, filtered.size());
        assertEquals("Product A", filtered.get(0).getName());
    }

    @Test
    @DisplayName("Проверка сортировки по цене в возрастающем порядке")
    void testSortByPriceAsc() {
        List<Product> result = productService.getFilteredAndSortedProducts(null, null, null, null, "price", "asc", 5);

        assertEquals(3, result.size());
        assertEquals("Product C", result.get(0).getName());
        assertEquals("Product A", result.get(1).getName());
        assertEquals("Product B", result.get(2).getName());
    }

    @Test
    @DisplayName("Проверка фильтрации товаров по диапазону цены")
    void testFilterByPriceRange() {
        List<Product> filtered = productService.getFilteredAndSortedProducts(null, 250.0, 900.0, null, "name", "asc", 5);
        assertEquals(2, filtered.size());
        assertEquals("Product A", filtered.get(0).getName());
    }

    @Test
    @DisplayName("Проверка фильтрации при примении нескольких фильтров")
    void testCombinedFilters() {
        List<Product> filtered = productService.getFilteredAndSortedProducts("Pr", 100.0, 10000.0, true, "price", "desc", 5);
        assertEquals(3, filtered.size());
        assertEquals("Product B", filtered.get(0).getName());
        assertEquals("Product A", filtered.get(1).getName());
        assertEquals("Product C", filtered.get(2).getName());
    }
}

