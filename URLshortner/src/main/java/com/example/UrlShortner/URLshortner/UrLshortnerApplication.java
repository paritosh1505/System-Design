package com.example.UrlShortner.URLshortner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class UrLshortnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrLshortnerApplication.class, args);
	}

}
