package com.hong_mae.nextjs_prj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NextjsPrjApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextjsPrjApplication.class, args);
	}

}
