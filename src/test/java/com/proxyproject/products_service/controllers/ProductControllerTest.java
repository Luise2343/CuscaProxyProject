package com.proxyproject.products_service.controllers;

import com.proxyproject.products_service.clients.FakeStoreClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FakeStoreClient fakeStoreClient;

    private static Map<String, Object> product(long id, String title, String price) {
        Map<String, Object> p = new HashMap<>();
        p.put("id", id);
        p.put("title", title);
        p.put("price", new BigDecimal(price));
        return p;
    }

    @Test
    @DisplayName("GET /api/products returns 200 and a JSON array")
    void shouldReturnListOfProducts() throws Exception {
        List<Map<String, Object>> payload = List.of(
                product(1L, "Product A", "10.99"),
                product(2L, "Product B", "20.50")
        );

        // ✅ coincide con tu FakeStoreClient
        Mockito.when(fakeStoreClient.getProducts()).thenReturn(payload);

        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Product A")))
                .andExpect(jsonPath("$[0].price", is(10.99)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Product B")))
                .andExpect(jsonPath("$[1].price", is(20.50)));
    }

    @Test
    @DisplayName("GET /api/products/{id} returns 200 and a JSON object")
    void shouldReturnSingleProductById() throws Exception {
        Map<String, Object> p = product(1L, "Product A", "10.99");

        // ✅ coincide con tu FakeStoreClient
        Mockito.when(fakeStoreClient.getProductById(anyLong())).thenReturn(p);

        mockMvc.perform(get("/api/products/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Product A")))
                .andExpect(jsonPath("$.price", is(10.99)));
    }
}
