package com.study_buddy.study_buddy;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.study_buddy.study_buddy.controller.ExampleController;

@SpringBootTest
class ExampleTest {

	@Autowired
	private ExampleController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void exampleReturnsHelloWorld() throws Exception {
		Map<String, String> helloWorld = Map.of("hello", "world");
		assertThat(controller.getExample()).isEqualTo(helloWorld);
	}
}
