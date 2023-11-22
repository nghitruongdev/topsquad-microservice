package com.ts.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",
            "/eureka/**"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf().disable();

        http.authorizeExchange(exchange -> exchange
                .pathMatchers(AUTH_WHITELIST).permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/inventories/init-data").permitAll()
                .anyExchange().authenticated()
        ).oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfiguration() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.applyPermitDefaultValues();
//        corsConfig.setAllowCredentials(true);
//        corsConfig.addAllowedOriginPattern("*");
//        corsConfig.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
//
//        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type", "Content-Type"));
//        corsConfig.setExposedHeaders(Arrays.asList("X-Get-Header"));
//
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
//
//    @Bean
//    CorsWebFilter webFilter() {
//        return new CorsWebFilter(corsConfiguration());
//    }


}
