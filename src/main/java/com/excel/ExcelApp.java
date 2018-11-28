package com.excel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExcelApp implements ApplicationRunner{
	
	@Autowired
	private ExcelDemo demo;
	
	public static void main(String[] args) {
		SpringApplication.run(ExcelApp.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		demo.make();
	}

	

}
