package rustamscode.onlineshopapi.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rustamscode.onlineshopapi.dto.SaleRequest;
import rustamscode.onlineshopapi.model.Sale;
import rustamscode.onlineshopapi.service.SaleService;

import java.util.List;

@RestController
@RequestMapping("/sales")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleController {
    final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Sale getSaleById(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Sale> createSale(@RequestBody SaleRequest saleRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saleService.createSale(saleRequest));
    }

    @PutMapping("/update/{id}")
    public Sale updateSale(@PathVariable Long id, @RequestBody SaleRequest saleRequest) {
        return saleService.updateSale(id, saleRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }
}
