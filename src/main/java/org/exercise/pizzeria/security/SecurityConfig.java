package org.exercise.pizzeria.security;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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


    /*
    home "/" USER ADMIN
    create edit delete ADMIN
    show "/pizzas/{id}" ADMIN USER
    premium-deal "/borrowing/tutti/ Admin
    categories "/categories/tutti" ADMIN
    * */

//    questo serve per far funzionare il css
//    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .requestMatchers("/api", "/api/**").permitAll()
                .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/","/pizzas", "/pizzas/{id}").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/ingredients", "/ingredients/**").hasAuthority("ADMIN")
                .requestMatchers("/premium", "/premium/**").hasAuthority("ADMIN")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).hasAnyAuthority("USER", "ADMIN")
                .and().formLogin().defaultSuccessUrl("/pizzas", true)
                .and().logout()
                .and().exceptionHandling();
        http.csrf().disable();
                return http.build();
    }


    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());

        provider.setPasswordEncoder(passwordEncoder());

        return  provider;
    }
}
