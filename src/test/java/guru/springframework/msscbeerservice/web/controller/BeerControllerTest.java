package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  static BeerDto getValidBeer() {
    return BeerDto.builder()
        .beerName("My bear")
        .beerStyle(BeerStyleEnum.ALE)
        .price(new BigDecimal("2.99"))
        .upc(123123123123L)
        .build();
  }

  @Test
  void getBeerById() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/beer/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void saveBeer() throws Exception {
    String beerJson = objectMapper.writeValueAsString(getValidBeer());
    mockMvc
        .perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerJson))
        .andExpect(status().isCreated());
  }

  @Test
  void updateBeerById() throws Exception {

    String beerJson = objectMapper.writeValueAsString(getValidBeer());

    mockMvc
        .perform(
            put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerJson))
        .andExpect(status().isNoContent());
  }
}
