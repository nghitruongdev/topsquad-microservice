package com.ts.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    /**
     * Using the WebClient.Builder allows ye to configure additional features, such as custom filters, interceptors, or error handling mechanisms, before building the WebClient instance. This separation allows for a cleaner and more modular approach to configuring and customizing the WebClient behavior.
     *
     * So, while it may seem a bit more verbose to have a separate WebClient.Builder bean, it provides greater flexibility and allows for more advanced customization options.
     */
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
