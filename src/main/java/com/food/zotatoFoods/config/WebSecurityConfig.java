package com.food.zotatoFoods.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.food.zotatoFoods.Security.JWTAuthFilter;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

        private static final String[] PUBLIC_ROUTES = { "/auth/**", "/v3/api-docs/**", "/swagger-ui.html",
                        "/swagger-ui/**" };

        private final JWTAuthFilter jwtAuthFilter;

        @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:8080,http://localhost:5173}")
        private List<String> allowedOrigins;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .sessionManagement(sessionConfig -> sessionConfig
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .csrf(csrfconfig -> csrfconfig.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(PUBLIC_ROUTES).permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(allowedOrigins);
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
