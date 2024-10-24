package com.study_buddy.study_buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.study_buddy.study_buddy.config.SecurityConfig;

@SpringBootApplication
@EntityScan("com.study_buddy.study_buddy.model")
public class StudyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyBuddyApplication.class, args);
	}

}
