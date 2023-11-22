package com.ts.apigateway.config;

import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    CommandLineRunner openApiGroups(
            RouteDefinitionLocator locator,
            SwaggerUiConfigParameters parameters
    ) {
        return args -> locator
                .getRouteDefinitions().toStream()
                .map(RouteDefinition::getId)
                .filter(id -> id.matches(".*-service") && !id.matches("discovery-service"))
//                .map(id -> id.replace("-service", ""))
                .forEach(parameters::addGroup);
    }
}
