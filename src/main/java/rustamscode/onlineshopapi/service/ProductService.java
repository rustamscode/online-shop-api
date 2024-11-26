package rustamscode.onlineshopapi.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rustamscode.onlineshopapi.dto.ProductRequest;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService {
    final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(@Valid ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.getName());
        product.setInfo(productRequest.getInfo());
        product.setPrice(BigDecimal.valueOf(productRequest.getPrice()));
        product.setAvailable(productRequest.isAvailable());

        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, @Valid ProductRequest productRequest) {
        Product existingProduct = getProductById(id);

        existingProduct.setName(productRequest.getName());
        existingProduct.setInfo(productRequest.getInfo());
        existingProduct.setPrice(BigDecimal.valueOf(productRequest.getPrice()));
        existingProduct.setAvailable(productRequest.isAvailable());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    public List<Product> getFilteredAndSortedProducts(String name, Double minPrice, Double maxPrice,
                                               Boolean available, String sortBy, String sortDirection, Integer limit) {
        if (name != null && name.length() > 255) {
            throw new IllegalArgumentException("Название товара слишком длинное");
        }

        if (minPrice != null && minPrice < 0) {
            throw new IllegalArgumentException("Минимальная цена не может быть отрицательной");
        }

        if (maxPrice != null && maxPrice < 0) {
            throw new IllegalArgumentException("Максимальная цена не может быть отрицательной");
        }

        if (!List.of("name", "price").contains(sortBy)) {
            throw new IllegalArgumentException("Указан неверный параметр сортировки. Доступны 'name' и 'price'.");
        }
        if (!List.of("asc", "desc").contains(sortDirection)) {
            throw new IllegalArgumentException("Указан неверный параметр направления сортировки. Доступны 'asc' и 'desc'.");
        }

        return productRepository.filterAndSort(
                name, minPrice, maxPrice, available, sortBy, sortDirection, limit);
    }

}