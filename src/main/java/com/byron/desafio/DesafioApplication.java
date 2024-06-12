package com.byron.desafio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.byron.desafio.principal.Principal;


@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
