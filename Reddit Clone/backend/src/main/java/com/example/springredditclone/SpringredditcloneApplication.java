package com.example.springredditclone;

import com.example.springredditclone.security.config.OpenAPIConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(OpenAPIConfig.class)
public class SpringredditcloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.example.springredditclone.SpringredditcloneApplication.class, args);
	}

}
