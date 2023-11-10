package de.neuefische.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${myapp.environment}")
    private String environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/secured/**").authenticated()
                        .requestMatchers("/api/auth/me").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l -> {
                    if (environment.equals("prod")) {
                        l.logoutSuccessUrl("/").permitAll();
                    } else {
                        l.logoutSuccessUrl("http://localhost:5173").permitAll();
                    }
                })
                .oauth2Login(o -> {
                    try {
                        o.init(http);
                        if (environment.equals("prod")) {
                            o.defaultSuccessUrl("/", true);
                        } else {
                            o.defaultSuccessUrl("http://localhost:5173", true);
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                });
        return http.build();

    }

}
