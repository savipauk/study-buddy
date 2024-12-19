package com.study_buddy.study_buddy.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login/**", "/users/**", "/example/greeting",
                            "/oauth2/**", "/h2-console/**", "/favicon.ico").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); // Allow OPTIONS
                    auth.anyRequest().authenticated();
                })
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Custom CORS configuration
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for
                                                       // testing??????????????????????????????????????????
                .addFilterBefore(customHeaderFilter(), HeaderWriterFilter.class) // Add the custom header filter
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromOidcIssuerLocation("https://accounts.google.com");
    }

    private Filter customHeaderFilter() {
        return (request, response, chain) -> {
            // Cast request to HttpServletRequest
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();

            // Check if the request URI is for OAuth2 authentication endpoints
            if (!requestURI.startsWith("/login/oauth") && !requestURI.startsWith("/oauth2")) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;

                // Set X-Frame-Options to SAMEORIGIN
                httpResponse.setHeader("X-Frame-Options", "SAMEORIGIN");

                // Set Content-Security-Policy to allow framing only from same origin and
                // localhost
                httpResponse.setHeader("Content-Security-Policy",
                        "frame-ancestors 'self' http://localhost:8080 https://accounts.google.com");

            }
            // Continue with the filter chain
            chain.doFilter(request, response);
        };
    }

    // Define CORS configuration source
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // Consider tightening this for specific origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;

    }

}
