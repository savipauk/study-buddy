package com.study_buddy.study_buddy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.study_buddy.study_buddy.config.SecurityConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestApiNotFoundTest {

	// @Override
	// public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws
	// Exception {
	// return http
	// .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Allow all
	// requests
	// .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity
	// .build();
	// }

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void nonExistingEndpointReturnsNotFound() throws Exception {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/nonExistingEndpoint", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
}
