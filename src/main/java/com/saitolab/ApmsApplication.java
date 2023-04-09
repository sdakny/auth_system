package com.saitolab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.saitolab.mapper")
public class ApmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApmsApplication.class, args);
	}

}
