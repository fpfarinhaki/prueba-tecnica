package com.decskill.prueba;

import com.decskill.prueba.domain.event.RequestPriceResponse;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PruebaPracticaApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@ParameterizedTest
	@CsvSource({
			"35455, 1, 2020-06-14 10:00:00, 1, 35.50",
			"35455, 1, 2020-06-14 16:00:00, 2, 25.45",
			"35455, 1, 2020-06-14 21:00:00, 1, 35.50",
			"35455, 1, 2020-06-15 10:00:00, 3, 30.50",
			"35455, 1, 2020-06-16 21:00:00, 4, 38.95"
	})
	void testFindFinalPriceResponse(Integer productId, Integer brandId,String applicationDate, Integer expectedPriceList, Double expectedFinalPrice) throws Exception {
		RequestPriceResponse response = new RequestPriceResponse(productId, brandId, expectedPriceList, applicationDate, expectedFinalPrice);
		String expectedJson = Json.mapper().writeValueAsString(response);
		this.mockMvc.perform(get("/price")
				.param("productId", String.valueOf(productId))
				.param("brandId", String.valueOf(brandId))
				.param("applicationDate", applicationDate))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}

}
