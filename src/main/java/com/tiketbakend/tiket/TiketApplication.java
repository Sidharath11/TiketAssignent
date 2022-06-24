package com.tiketbakend.tiket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TiketApplication  {
	public static void main(String[] args) {
		SpringApplication.run(TiketApplication.class, args);
	}
}