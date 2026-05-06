package com.betacom.jpa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();		
    } 

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/rest/auth/login").permitAll()
            	.requestMatchers("/rest/auth/refresh").permitAll()
            	.requestMatchers("/rest/auth/logout").permitAll()
                .requestMatchers("/rest/utente/public/**").permitAll()
                .requestMatchers("/rest/categoria/public/**").permitAll()
                .requestMatchers("/rest/colore/public/**").permitAll()
                .requestMatchers("/rest/alimentazione/public/**").permitAll()
                .requestMatchers("/rest/tipoVeicolo/public/**").permitAll()
                .requestMatchers("/rest/marca/public/**").permitAll()
                .requestMatchers("/rest/veicolo/public/**").permitAll()
                .requestMatchers("/rest/veicolo/public/**").permitAll()
                .requestMatchers("/images/**").permitAll() 
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                
                .requestMatchers("/rest/utente/admin/**").hasRole("ADMIN")
                .requestMatchers("/rest/macchina/admin/**").hasRole("ADMIN")
                .requestMatchers("/rest/upload/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth.jwt(jwt ->
                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
            ));

        return http.build();
    }

     
 
}


 
