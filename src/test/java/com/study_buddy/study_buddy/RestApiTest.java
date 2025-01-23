package com.study_buddy.study_buddy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class RestApiTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void exampleRestApiCallReturnsHelloWorld() throws Exception {
		Map<String, String> helloWorld = Map.of("hello", "world");
		// No need for a DTO because this is not a complex object
		ResponseEntity<Map<String, String>> response = this.restTemplate.exchange(
				"/example/greeting",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {
				});
		assertThat(response.getBody()).isEqualTo(helloWorld);
	}

	@Test
	void nonExistingEndpointReturnsNotFound() throws Exception {
		ResultActions response = mockMvc.perform(get("/nonExistingEndpoint")).andExpect(status().isNotFound());

		response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void unauthorizedEndpointReturnsUnauthorized() throws Exception {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/example/unauthorized", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
}
