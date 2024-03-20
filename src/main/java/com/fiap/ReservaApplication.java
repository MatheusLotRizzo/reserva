package com.fiap;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReservaApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(ReservaApplication.class, args);
	}

}
