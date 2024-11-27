package rustamscode.onlineshopapi.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rustamscode.onlineshopapi.dto.SupplyRequest;
import rustamscode.onlineshopapi.exception.SupplyNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Supply;
import rustamscode.onlineshopapi.repository.ProductRepository;
import rustamscode.onlineshopapi.repository.SupplyRepository;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplyServiceTest {

    @Mock
    private SupplyRepository supplyRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SupplyService supplyService;

    @Test
    @DisplayName("Проверка получения списка всех поставок")
    void getAllSuppliesShouldReturnAllSupplies() {
        when(supplyRepository.findAll()).thenReturn(List.of(new Supply(), new Supply()));

        var result = supplyService.getAllSupplies();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(supplyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Проверка получения существующих поставок по ID")
    void getSupplyByIdShouldReturnSupplyWhenExists() {
        Long supplyId = 1L;
        Supply supply = new Supply();
        supply.setId(supplyId);
        when(supplyRepository.findById(supplyId)).thenReturn(Optional.of(supply));

        var result = supplyService.getSupplyById(supplyId);

        assertNotNull(result);
        assertEquals(supplyId, result.getId());
        verify(supplyRepository, times(1)).findById(supplyId);
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке получить несущетсвующую поставку")
    void getSupplyByIdShouldThrowExceptionWhenNotFound() {
        Long supplyId = 1L;
        when(supplyRepository.findById(supplyId)).thenReturn(Optional.empty());

        assertThrows(SupplyNotFoundException.class, () -> supplyService.getSupplyById(supplyId));
        verify(supplyRepository, times(1)).findById(supplyId);
    }

    @Test
    @DisplayName("Проверка создания поставки при передаче правильных аргументов")
    void createSupplyShouldCreateSupplyWhenValidRequest() {
        Long productId = 1L;
        int amount = 10;
        Product product = new Product();
        product.setId(productId);
        product.setStock(20);

        SupplyRequest supplyRequest = new SupplyRequest("Test Supply", productId, amount);

        when(productService.getProductById(productId)).thenReturn(product);
        when(supplyRepository.save(any(Supply.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Supply createdSupply = supplyService.createSupply(supplyRequest);

        assertNotNull(createdSupply);
        assertEquals("Test Supply", createdSupply.getName());
        assertEquals(30, product.getStock()); // Updated stock after supply
        verify(productValidator, times(1)).exists(productId);
        verify(productRepository, times(1)).save(product);
        verify(supplyRepository, times(1)).save(any(Supply.class));
    }

    @Test
    @DisplayName("Проверка обновления поставок при передаче правильных аргументов")
    void updateSupplyShouldUpdateSupplyWhenValidRequest() {
        Long supplyId = 1L;
        Long productId = 1L;
        int amount = 15;

        Product product = new Product();
        product.setId(productId);
        Supply existingSupply = new Supply();
        existingSupply.setId(supplyId);

        SupplyRequest supplyRequest = new SupplyRequest("Updated Supply", productId, amount);

        when(supplyRepository.findById(supplyId)).thenReturn(Optional.of(existingSupply));
        when(productService.getProductById(productId)).thenReturn(product);
        when(supplyRepository.save(any(Supply.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Supply updatedSupply = supplyService.updateSupply(supplyId, supplyRequest);

        assertNotNull(updatedSupply);
        assertEquals("Updated Supply", updatedSupply.getName());
        assertEquals(product, updatedSupply.getProduct());
        assertEquals(amount, updatedSupply.getAmount());
        verify(productValidator, times(1)).exists(productId);
        verify(supplyRepository, times(1)).save(existingSupply);
    }

    @Test
    @DisplayName("Проверка удаления существующих продаж")
    void deleteSupplyShouldDeleteSupplyWhenExists() {
        Long supplyId = 1L;
        when(productRepository.existsById(supplyId)).thenReturn(true);


        supplyService.deleteSupply(supplyId);

        verify(productRepository, times(1)).deleteById(supplyId);
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке удаления несуществующих поставок")
    void deleteSupplyShouldThrowExceptionWhenNotExists() {
        Long supplyId = 1L;
        when(productRepository.existsById(supplyId)).thenReturn(false);

        assertThrows(SupplyNotFoundException.class, () -> supplyService.deleteSupply(supplyId));
        verify(productRepository, never()).deleteById(supplyId);
    }
}
