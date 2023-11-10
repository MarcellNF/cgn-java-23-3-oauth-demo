package de.neuefische.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

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
                        .anyRequest().permitAll()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
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
