package rustamscode.onlineshopapi.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import rustamscode.onlineshopapi.exception.ProductNotFoundException;
import rustamscode.onlineshopapi.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductService {
    final Map<Long, Product> productStore = new ConcurrentHashMap<>();
    final AtomicLong idCounter = new AtomicLong(1);

    public List<Product> getAllProducts() {
        return new ArrayList<>(productStore.values());
    }

    public Product getProductById(Long id) {
        return Optional.ofNullable(productStore.get(id))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        product.setId(idCounter.getAndIncrement());
        productStore.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setInfo(updatedProduct.getInfo());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setAvailable(updatedProduct.isAvailable());
        return existingProduct;
    }

    public void deleteProduct(Long id) {
        if (!productStore.containsKey(id)) {
            throw new ProductNotFoundException(id);
        }
        productStore.remove(id);
    }
}
