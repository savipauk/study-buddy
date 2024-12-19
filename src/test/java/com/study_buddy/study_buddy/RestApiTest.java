package com.study_buddy.study_buddy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestApiTest {

	@Autowired
	private TestRestTemplate restTemplate;

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

	// TODO: Create a mock for security config
	@Test
	void nonExistingEndpointReturnsNotFound() throws Exception {
		Map<String, Object> attributes = Map.of(
				"sub", "123456",
				"email", "authorizeduser@example.com");

		DefaultOAuth2User mockUser = new DefaultOAuth2User(
				Set.of(new OAuth2UserAuthority(attributes)),
				attributes,
				"email");

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities()));

		try {
			ResponseEntity<String> response = this.restTemplate.getForEntity("/nonExistingEndpoint", String.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		} finally {
			SecurityContextHolder.clearContext();
		}

	}

	@Test
	void unauthorizedEndpointReturnsUnauthorized() throws Exception {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/example/unauthorized", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
}
