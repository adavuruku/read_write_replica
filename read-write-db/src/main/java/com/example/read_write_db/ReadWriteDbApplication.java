package com.example.read_write_db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class ReadWriteDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReadWriteDbApplication.class, args);
	}

}
