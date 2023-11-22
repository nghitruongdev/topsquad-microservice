package com.ts.inventoryservice;

import com.ts.inventoryservice.repository.InventoryRepository;
import com.ts.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @Profile("docker")
    CommandLineRunner loadData(InventoryService service, InventoryRepository repository) {
        return args -> {
            while (repository.count() == 0) {
                log.info("Trying to initialize inventory data");
                try{
                    service.initializeData();
                    Thread.sleep(10 * 1000);
                }catch (Exception ex){
                    log.error("Error occurred while initializing inventory data: {}", ex.getMessage());
                }
            }

        };
    }
}
