package com.ts.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.ts.productservice.model.Product;
import com.ts.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

	@Bean
	CommandLineRunner initializeData(
			ProductRepository productRepository
	) {
		return args -> {
			if (productRepository.count() > 0) {
				return;
			}
			var mapper = new ObjectMapper();
			var resource = new ClassPathResource("products.json");
			productRepository.saveAll(Arrays.asList(mapper
					.readValue(resource.getInputStream(), Product[].class)));
			System.out.println("Initialized product data");
		};
	}
}
