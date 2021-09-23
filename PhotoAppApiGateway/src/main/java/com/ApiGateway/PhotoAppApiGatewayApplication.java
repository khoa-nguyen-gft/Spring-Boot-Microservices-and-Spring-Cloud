package com.ApiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class PhotoAppApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoAppApiGatewayApplication.class, args);
	}

}


@RestController
@RequestMapping("/gateway")
class PhotoAppApiGateway {

	@Autowired
	private Environment env;

	@GetMapping("/status/check")
	public String status() {
		return "Working on the port: " + env.getProperty("local.server.port")  +
				" token: " + env.getProperty("token.secret");
	}
}