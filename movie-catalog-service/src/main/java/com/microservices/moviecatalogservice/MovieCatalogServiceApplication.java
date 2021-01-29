package com.microservices.moviecatalogservice;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
public class MovieCatalogServiceApplication {

	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		
		/*
		 * HttpComponentsClientHttpRequestFactory client=new
		 * HttpComponentsClientHttpRequestFactory(); client.setConnectTimeout(3000);
		 * return new RestTemplate(client);
		 */
	    return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
	}
	
	
	  @Bean
	  @LoadBalanced 
	  public WebClient.Builder getWebClient(){ return
	  WebClient.builder(); }
	 
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

}
