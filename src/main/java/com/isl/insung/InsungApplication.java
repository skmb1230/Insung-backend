package com.isl.insung;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication	
@ComponentScan(basePackages = {"com"})
@MapperScan(basePackages = {"com"})
public class InsungApplication {

	public static void main(String[] args) {

		//System.out.println("test :: "+args[0]);
		SpringApplication.run(InsungApplication.class, args);
		
	}

}
