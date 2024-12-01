package rustamscode.onlineshopapi.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import rustamscode.onlineshopapi.dto.SupplyRequest;
import rustamscode.onlineshopapi.exception.SupplyNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Supply;
import rustamscode.onlineshopapi.repository.ProductRepository;
import rustamscode.onlineshopapi.repository.SupplyRepository;

import java.util.List;

@Service
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyService {
    final SupplyRepository supplyRepository;
    final ProductValidator productValidator;
    final ProductRepository productRepository;
    final ProductService productService;

    public SupplyService(SupplyRepository supplyRepository, ProductValidator productValidator, ProductRepository productRepository, ProductService productService) {
        this.supplyRepository = supplyRepository;
        this.productValidator = productValidator;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply getSupplyById(Long id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new SupplyNotFoundException(id));
    }

    @Transactional
    public Supply createSupply(@Valid SupplyRequest supplyRequest) {
        Long productId = supplyRequest.getProductId();
        int amount = supplyRequest.getAmount();

        productValidator.exists(productId);

        Supply supply = new Supply();
        Product product = productService.getProductById(productId);

        product.setStock(product.getStock() + amount);
        productRepository.save(product);

        supply.setName(supplyRequest.getName());
        supply.setProduct(product);
        supply.setAmount(amount);

        return supplyRepository.save(supply);
    }

    public Supply updateSupply(Long id, @Valid SupplyRequest supplyRequest) {
        Long productId = supplyRequest.getProductId();

        productValidator.exists(productId);

        Supply existingSupply = getSupplyById(id);
        Product product = productService.getProductById(productId);

        existingSupply.setName(supplyRequest.getName());
        existingSupply.setProduct(product);
        existingSupply.setAmount(supplyRequest.getAmount());

        return supplyRepository.save(existingSupply);
    }

    public void deleteSupply(Long id) {
        if (!productRepository.existsById(id)) {
            throw new SupplyNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
