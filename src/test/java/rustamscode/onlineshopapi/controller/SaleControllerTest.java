package rustamscode.onlineshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rustamscode.onlineshopapi.dto.SaleRequest;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Sale;
import rustamscode.onlineshopapi.service.SaleService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SaleController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class SaleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    SaleService saleService;

    @Autowired
    ObjectMapper objectMapper;

    Sale sale1;
    Sale sale2;
    List<Sale> saleList;

    @BeforeEach
    void setUp() {
        sale1 = new Sale(1L, "Sale 1", new Product(), 10, BigDecimal.valueOf(100));
        sale2 = new Sale(2L, "Sale 2", new Product(), 5, BigDecimal.valueOf(50));
        saleList = Arrays.asList(sale1, sale2);
    }

    @Test
    @DisplayName("Проверка получения всех продаж по адресу \"/sales\"")
    void getAllSalesShouldReturnAllSales() throws Exception {
        when(saleService.getAllSales()).thenReturn(saleList);

        mockMvc.perform(get("/sales")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(saleList.size()))
                .andExpect(jsonPath("$[0].id").value(sale1.getId()))
                .andExpect(jsonPath("$[0].name").value(sale1.getName()))
                .andExpect(jsonPath("$[1].id").value(sale2.getId()))
                .andExpect(jsonPath("$[1].name").value(sale2.getName()));

        verify(saleService, times(1)).getAllSales();
    }

    @Test
    @DisplayName("Проверка получения продажи по ID по адресу \"/sales/{id}\", где {id}" +
            "является переменной пути для запроса")
    void getSaleByIdShouldReturnSaleWhenExists() throws Exception {
        Long saleId = sale1.getId();
        when(saleService.getSaleById(saleId)).thenReturn(sale1);

        mockMvc.perform(get("/sales/{id}", saleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sale1.getId()))
                .andExpect(jsonPath("$.name").value(sale1.getName()));

        verify(saleService, times(1)).getSaleById(saleId);
    }

    @Test
    @DisplayName("Проверка успешного создания продажи по адресу /sales/create с" +
            " передачей SaleRequest (DTO) в тело запроса")
    void createSaleShouldReturnCreatedSaleWhenValidRequest() throws Exception {
        SaleRequest saleRequest = new SaleRequest("Sale 1", 1L, 10);
        Sale createdSale = new Sale(1L, saleRequest.getName(), new Product(), saleRequest.getAmount(), BigDecimal.valueOf(100));
        when(saleService.createSale(any(SaleRequest.class))).thenReturn(createdSale);

        mockMvc.perform(post("/sales/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdSale.getId()))
                .andExpect(jsonPath("$.name").value(createdSale.getName()));

        verify(saleService, times(1)).createSale(any(SaleRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного обновления продажи по ID по " +
            "адресу \"/sales/update/{id}\", где {id} является переменной пути " +
            "для запроса, необходимо передавать SalesRequest (DTO) в тело запроса")
    void updateSaleShouldReturnUpdatedSaleWhenValidRequest() throws Exception {
        Long saleId = sale1.getId();
        SaleRequest saleRequest = new SaleRequest("Updated Sale", 1L, 15);
        Sale updatedSale = new Sale(saleId, saleRequest.getName(), new Product(), saleRequest.getAmount(), BigDecimal.valueOf(150));
        when(saleService.updateSale(eq(saleId), any(SaleRequest.class))).thenReturn(updatedSale);

        mockMvc.perform(put("/sales/update/{id}", saleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedSale.getId()))
                .andExpect(jsonPath("$.name").value(updatedSale.getName()));

        verify(saleService, times(1)).updateSale(eq(saleId), any(SaleRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного удаления продажи по адрсесу /sales/delete/{id}, где" +
            "{id} является переменной пути для запроса")
    void deleteSaleShouldReturnNoContentWhenSuccessful() throws Exception {
        Long saleId = sale1.getId();
        doNothing().when(saleService).deleteSale(saleId);

        mockMvc.perform(delete("/sales/delete/{id}", saleId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(saleService, times(1)).deleteSale(saleId);
    }
}
