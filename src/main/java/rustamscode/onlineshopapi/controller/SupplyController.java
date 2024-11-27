package rustamscode.onlineshopapi.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rustamscode.onlineshopapi.dto.SupplyRequest;
import rustamscode.onlineshopapi.model.Supply;
import rustamscode.onlineshopapi.service.SupplyService;

import java.util.List;

@RestController
@RequestMapping("/supplies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplyController {
    final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping
    public List<Supply> getAllSupplies() {
        return supplyService.getAllSupplies();
    }

    @GetMapping("/{id}")
    public Supply getSupplyById(@PathVariable Long id) {
        return supplyService.getSupplyById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Supply> createSupply(@RequestBody SupplyRequest supplyRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplyService.createSupply(supplyRequest));
    }

    @PutMapping("/update/{id}")
    public Supply updatedSupply(@PathVariable Long id,
                                @RequestBody SupplyRequest supplyRequest) {
        return supplyService.updateSupply(id, supplyRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
    }
}
