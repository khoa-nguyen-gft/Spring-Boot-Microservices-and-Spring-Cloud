package com.account.nanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AccountManager {

	public static void main(String[] args) {
		SpringApplication.run(AccountManager.class, args);
	}

}
