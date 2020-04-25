package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean BeerService beerService;

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
    given(beerService.getById(any(), any())).willReturn(getValidBeer());
    mockMvc
        .perform(
            get("/api/v1/beer/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void saveBeer() throws Exception {
    String beerJson = objectMapper.writeValueAsString(getValidBeer());
    given(beerService.saveNewBeer(any())).willReturn(getValidBeer());
    mockMvc
        .perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerJson))
        .andExpect(status().isCreated());
  }

  @Test
  void updateBeerById() throws Exception {

    String beerJson = objectMapper.writeValueAsString(getValidBeer());
    given(beerService.updateBeer(any(), any())).willReturn(getValidBeer());
    mockMvc
        .perform(
            put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerJson))
        .andExpect(status().isNoContent());
  }
}
