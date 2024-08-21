package com.batch.processing;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Application.main() started at :: " + new Date());
		SpringApplication.run(Application.class, args);
	}

}
