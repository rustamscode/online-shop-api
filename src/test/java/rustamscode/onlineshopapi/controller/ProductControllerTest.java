package rustamscode.onlineshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rustamscode.onlineshopapi.dto.ProductRequest;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.service.ProductService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    Product product1;
    Product product2;
    List<Product> productList;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Product A", "Description A", BigDecimal.valueOf(1900), 5, true);
        product2 = new Product(2L, "Product B", "Description B", BigDecimal.valueOf(1200), 2, true);
        productList = Arrays.asList(product1, product2);
    }

    @Test
    @DisplayName("Проверка получения всех продуктов по адресу \"/products\"")
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[0].name").value(product1.getName()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()))
                .andExpect(jsonPath("$[1].name").value(product2.getName()));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Проверка получения отфильтрованных и отсортированных продуктов" +
            "по адресу \"/products/filter\" с передачей фильтров в параметры запроса")
    void testGetFilteredAndSortedProducts() throws Exception {
        when(productService.getFilteredAndSortedProducts(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(productList);

        mockMvc.perform(get("/products/filter")
                        .param("name", "Product")
                        .param("minPrice", "50")
                        .param("maxPrice", "150")
                        .param("available", "true")
                        .param("sortBy", "price")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product A"));

        verify(productService, times(1))
                .getFilteredAndSortedProducts("Product", 50.0, 150.0, true, "price", "asc", 5);
    }

    @Test
    @DisplayName("Проверка получения продукта по ID по адресу \"/products/{id}\", где {id}" +
            "является переменной пути для запроса")
    void testGetProductById() throws Exception {
        when(productService.getProductById(product1.getId())).thenReturn(product1);

        mockMvc.perform(get("/products/{id}", product1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product1.getId()))
                .andExpect(jsonPath("$.name").value(product1.getName()));

        verify(productService, times(1)).getProductById(product1.getId());
    }

    @Test
    @DisplayName("Проверка успешного создания продукта по адресу /products/create с" +
            " передачей ProductRequest (DTO) в тело запроса")
    void testCreateProduct() throws Exception {
        ProductRequest request = new ProductRequest("New Product", "New Description", 300.0);
        Product createdProduct = new Product(3L, "New Product", "New Description", BigDecimal.valueOf(300.0), 5, true);

        when(productService.createProduct(any(ProductRequest.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdProduct.getId()))
                .andExpect(jsonPath("$.name").value(createdProduct.getName()));

        verify(productService, times(1)).createProduct(any(ProductRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного обновления продукта по его ID по " +
            "адресу \"/products/update/{id}\", где {id} является переменной пути " +
            "для запроса, необходимо передавать ProductRequest (DTO) в тело запроса")
    void testUpdateProduct() throws Exception {
        ProductRequest updateRequest = new ProductRequest("Updated Product", "Updated Description", 400.0);
        Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", BigDecimal.valueOf(400.0), 5, true);

        when(productService.updateProduct(eq(product1.getId()), any(ProductRequest.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/update/{id}", product1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedProduct.getId()))
                .andExpect(jsonPath("$.name").value(updatedProduct.getName()));

        verify(productService, times(1)).updateProduct(eq(product1.getId()), any(ProductRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного удаления продукта по адрсесу /products/delete/{id}, где" +
            "{id} является переменной пути для запроса")
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(product1.getId());

        mockMvc.perform(delete("/products/delete/{id}", product1.getId()))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(product1.getId());
    }

}
