package com.tiketbakend.tiket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiketApplication  {


	public static void main(String[] args) {
		//System.setProperty("spring.profiles.active", "errorhandling");

		SpringApplication.run(TiketApplication.class, args);
	}
}