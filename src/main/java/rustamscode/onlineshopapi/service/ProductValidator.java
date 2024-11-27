package rustamscode.onlineshopapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rustamscode.onlineshopapi.exception.InsufficientStockException;
import rustamscode.onlineshopapi.exception.OutOfStockException;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.repository.ProductRepository;

@Component
public class ProductValidator {
    final ProductRepository productRepository;
    final ProductService productService;

    @Autowired
    public ProductValidator(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public void exists(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }

    public void available(Long productId) {
        Product product = productService.getProductById(productId);
        if (!product.isAvailable()) {
            throw new OutOfStockException(productId);
        }
    }

    public void sufficient(Long productId, int saleAmount) {
        Product product = productService.getProductById(productId);
        if (product.getStock() < saleAmount) {
            throw new InsufficientStockException(productId);
        }
    }
}
