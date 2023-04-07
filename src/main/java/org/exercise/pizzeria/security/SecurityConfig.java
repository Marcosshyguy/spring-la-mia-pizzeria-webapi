package org.exercise.pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(){
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());

        provider.setPasswordEncoder(passwordEncoder());

        return  provider;
    }

    /*
    home "/" USER ADMIN
    create edit delete ADMIN
    show "/pizzas/{id}" ADMIN USER
    premium-deal "/borrowing/tutti/ Admin
    categories "/categories/tutti" ADMIN
    * */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests().requestMatchers("/ingredients", "/ingredients/**").hasAuthority("Admin")
                .requestMatchers("/premium/**").hasAuthority("admin")
                .requestMatchers("/pizzas", "pizzas/**").hasAuthority("User")
//                .requestMatchers(HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE , "").hasAuthority("Admin")
                .and().formLogin().and().logout().and().exceptionHandling();
                return http.build();
    }
}
