package com.example.kalps.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.kalps.Security.JwtFilter;
import com.example.kalps.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtFilter jwtFilter;
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                
                .requestMatchers(HttpMethod.POST,"api/users").permitAll()
                .requestMatchers("/api/users/**").authenticated()
                .requestMatchers("/home").permitAll()
                .anyRequest().permitAll()

            )
            
//            .formLogin(form -> form.permitAll().defaultSuccessUrl("/dashboard",true))
        	.csrf(csrf->csrf.disable())
        	.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
        	;
        	
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailService() {
//        UserDetails user = User.withUsername("Kalps")
//                .password(passwordEncoder.encode("kalps@123"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("Raja")
//                .password(passwordEncoder.encode("raja@123"))
//                .roles("ADMIN")
//                .build();
//    	return new InnMemoryUserDetailsManager(user,admin);

        return new CustomUserDetailsService();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(List.of(authenticationProvider()))  ;
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailService());
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
