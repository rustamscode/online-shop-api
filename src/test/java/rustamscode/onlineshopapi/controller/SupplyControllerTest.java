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
import rustamscode.onlineshopapi.dto.SupplyRequest;
import rustamscode.onlineshopapi.model.Product;
import rustamscode.onlineshopapi.model.Supply;
import rustamscode.onlineshopapi.service.SupplyService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplyController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class SupplyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    SupplyService supplyService;

    @Autowired
    ObjectMapper objectMapper;

    Supply supply1;
    Supply supply2;
    List<Supply> supplyList;

    @BeforeEach
    void setUp() {
        supply1 = new Supply(1L, "Supply 1", new Product(), 50);
        supply2 = new Supply(2L, "Supply 2", new Product(), 100);
        supplyList = Arrays.asList(supply1, supply2);
    }

    @Test
    @DisplayName("Проверка получения всех поставок по адресу \"/supplies\"")
    void getAllSuppliesShouldReturnAllSupplies() throws Exception {
        when(supplyService.getAllSupplies()).thenReturn(supplyList);

        mockMvc.perform(get("/supplies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(supplyList.size()))
                .andExpect(jsonPath("$[0].id").value(supply1.getId()))
                .andExpect(jsonPath("$[0].name").value(supply1.getName()))
                .andExpect(jsonPath("$[1].id").value(supply2.getId()))
                .andExpect(jsonPath("$[1].name").value(supply2.getName()));

        verify(supplyService, times(1)).getAllSupplies();
    }

    @Test
    @DisplayName("Проверка получения поставки по ID по адресу \"/supplies/{id}\", где {id}" +
            "является переменной пути для запроса")
    void getSupplyByIdShouldReturnSupplyWhenExists() throws Exception {
        Long supplyId = supply1.getId();
        when(supplyService.getSupplyById(supplyId)).thenReturn(supply1);

        mockMvc.perform(get("/supplies/{id}", supplyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(supply1.getId()))
                .andExpect(jsonPath("$.name").value(supply1.getName()));

        verify(supplyService, times(1)).getSupplyById(supplyId);
    }

    @Test
    @DisplayName("Проверка успешного создания поставки по адресу /supplies/create с" +
            " передачей SupplyRequest (DTO) в тело запроса")
    void createSupplyShouldReturnCreatedSupplyWhenValidRequest() throws Exception {
        SupplyRequest supplyRequest = new SupplyRequest("Supply 1", 1L, 50);
        Supply createdSupply = new Supply(1L, supplyRequest.getName(), new Product(), supplyRequest.getAmount());
        when(supplyService.createSupply(any(SupplyRequest.class))).thenReturn(createdSupply);

        mockMvc.perform(post("/supplies/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplyRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdSupply.getId()))
                .andExpect(jsonPath("$.name").value(createdSupply.getName()));

        verify(supplyService, times(1)).createSupply(any(SupplyRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного обновления поставки по ID по " +
            "адресу \"/supplies/update/{id}\", где {id} является переменной пути " +
            "для запроса, необходимо передавать SupplyRequest (DTO) в тело запроса")
    void updatedSupplyShouldReturnUpdatedSupplyWhenValidRequest() throws Exception {
        Long supplyId = supply1.getId();
        SupplyRequest supplyRequest = new SupplyRequest("Updated Supply", 1L, 75);
        Supply updatedSupply = new Supply(supplyId, supplyRequest.getName(), new Product(), supplyRequest.getAmount());
        when(supplyService.updateSupply(eq(supplyId), any(SupplyRequest.class))).thenReturn(updatedSupply);

        mockMvc.perform(put("/supplies/update/{id}", supplyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedSupply.getId()))
                .andExpect(jsonPath("$.name").value(updatedSupply.getName()));

        verify(supplyService, times(1)).updateSupply(eq(supplyId), any(SupplyRequest.class));
    }

    @Test
    @DisplayName("Проверка успешного удаления поставки по адрсесу /products/delete/{id}, где" +
            "{id} является переменной пути для запроса")
    void deleteSupplyShouldReturnNoContentWhenSuccessful() throws Exception {
        Long supplyId = supply1.getId();
        doNothing().when(supplyService).deleteSupply(supplyId);

        mockMvc.perform(delete("/supplies/delete/{id}", supplyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(supplyService, times(1)).deleteSupply(supplyId);
    }
}
