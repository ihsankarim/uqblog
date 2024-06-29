package com.uqblog.serverapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerappApplication.class, args);
		System.out.println("\nServerapp UQBlog is running!...\n");
	}

}
