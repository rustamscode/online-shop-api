package rustamscode.onlineshopapi.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rustamscode.onlineshopapi.dto.ProductRequest;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
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

    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();

        product.setName(productRequest.getName());
        product.setInfo(productRequest.getInfo());
        product.setPrice(BigDecimal.valueOf(productRequest.getPrice()));
        product.setAvailable(productRequest.isAvailable());

        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
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
}