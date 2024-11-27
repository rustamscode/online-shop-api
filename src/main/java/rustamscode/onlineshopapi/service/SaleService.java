package rustamscode.onlineshopapi.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import rustamscode.onlineshopapi.dto.SaleRequest;
import rustamscode.onlineshopapi.exception.SaleNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Sale;
import rustamscode.onlineshopapi.repository.ProductRepository;
import rustamscode.onlineshopapi.repository.SaleRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleService {
    final SaleRepository saleRepository;
    final ProductValidator productValidator;
    final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductValidator productValidator, ProductService productService, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productValidator = productValidator;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
    }

    public Sale createSale(@Valid SaleRequest saleRequest) {
        Long productId = saleRequest.getProductId();
        int amount = saleRequest.getAmount();

        productValidator.exists(productId);
        productValidator.available(productId);
        productValidator.sufficient(productId, amount);

        Sale sale = new Sale();
        Product product = productService.getProductById(productId);

        product.setStock(product.getStock() - amount);
        productRepository.save(product);

        sale.setName(saleRequest.getName());
        sale.setProduct(product);
        sale.setAmount(amount);
        sale.setPrice(product.getPrice().multiply(BigDecimal.valueOf(amount)));

        return saleRepository.save(sale);
    }

    public Sale updateSale(Long id, @Valid SaleRequest saleRequest) {
        Long productId = saleRequest.getProductId();
        int amount = saleRequest.getAmount();

        productValidator.exists(productId);
        productValidator.available(productId);
        productValidator.sufficient(productId, amount);

        Sale existingSale = getSaleById(id);
        Product product = productService.getProductById(productId);

        existingSale.setName(saleRequest.getName());
        existingSale.setProduct(product);
        existingSale.setAmount(amount);
        existingSale.setPrice(product.getPrice().multiply(BigDecimal.valueOf(amount)));

        return saleRepository.save(existingSale);
    }

    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException(id);
        }
        saleRepository.deleteById(id);
    }
}
