package rustamscode.onlineshopapi.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rustamscode.onlineshopapi.dto.SaleRequest;
import rustamscode.onlineshopapi.exception.SaleNotFoundException;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Sale;
import rustamscode.onlineshopapi.repository.ProductRepository;
import rustamscode.onlineshopapi.repository.SaleRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SaleService saleService;

    @Test
    @DisplayName("Проверка получения списка всех продаж")
    void getAllSalesShouldReturnAllSales() {
        when(saleRepository.findAll()).thenReturn(List.of(new Sale(), new Sale()));

        var result = saleService.getAllSales();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Проверка получения существующих продаж по ID")
    void getSaleByIdShouldReturnSaleWhenExists() {
        Long saleId = 1L;
        Sale sale = new Sale();
        sale.setId(saleId);
        when(saleRepository.findById(saleId)).thenReturn(Optional.of(sale));

        var result = saleService.getSaleById(saleId);

        assertNotNull(result);
        assertEquals(saleId, result.getId());
        verify(saleRepository, times(1)).findById(saleId);
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке получить несущетсвующую продажу")
    void getSaleByIdShouldThrowExceptionWhenNotFound() {
        Long saleId = 1L;
        when(saleRepository.findById(saleId)).thenReturn(Optional.empty());

        assertThrows(SaleNotFoundException.class, () -> saleService.getSaleById(saleId));
        verify(saleRepository, times(1)).findById(saleId);
    }

    @Test
    @DisplayName("Проверка создания продажи при передаче правильных аргументов")
    void createSaleShouldCreateSaleWhenValidRequest() {
        Long productId = 1L;
        int amount = 5;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10));
        product.setStock(10);

        SaleRequest saleRequest = new SaleRequest("Test Sale", productId, amount);

        when(productService.getProductById(productId)).thenReturn(product);
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Sale createdSale = saleService.createSale(saleRequest);

        assertNotNull(createdSale);
        assertEquals("Test Sale", createdSale.getName());
        assertEquals(BigDecimal.valueOf(50), createdSale.getPrice());
        verify(productValidator, times(1)).exists(productId);
        verify(productValidator, times(1)).available(productId);
        verify(productValidator, times(1)).sufficient(productId, amount);
        verify(productRepository, times(1)).save(product);
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    @DisplayName("Проверка удаления существующих продаж")
    void deleteSaleShouldDeleteSaleWhenExists() {
        Long saleId = 1L;
        when(saleRepository.existsById(saleId)).thenReturn(true);

        saleService.deleteSale(saleId);

        verify(saleRepository, times(1)).deleteById(saleId);
    }

    @Test
    @DisplayName("Проверка выброса исключения при попытке удаления несуществующих продаж")
    void deleteSaleShouldThrowExceptionWhenNotExists() {
        Long saleId = 1L;
        when(saleRepository.existsById(saleId)).thenReturn(false);

        assertThrows(SaleNotFoundException.class, () -> saleService.deleteSale(saleId));
        verify(saleRepository, never()).deleteById(saleId);
    }
}

