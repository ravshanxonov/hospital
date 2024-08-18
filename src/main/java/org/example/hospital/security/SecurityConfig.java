package org.example.hospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, CustomUserDetails customUserDetails) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(manager ->{
            manager.requestMatchers("/register","/error","/login","/css/**").permitAll()
                    .anyRequest().authenticated();

        });
        http.userDetailsService(customUserDetails);
        http.formLogin( m -> {
         m.loginPage("/login")
        .defaultSuccessUrl("/")
                 .failureHandler((request,response,exception)->{
                     response.sendRedirect("/login?error=true");
                 });
        });
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
