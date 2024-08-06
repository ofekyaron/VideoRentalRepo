package com.example.VideoRentalNew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.VideoRentalNew.repository")
public class VideoRentalNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoRentalNewApplication.class, args);
	}

}
