package com.chengfei.buyee.admin.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
	return new BuyeeUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests()
	    .requestMatchers("/users/**").hasAuthority("Admin")
	    .requestMatchers("/categories/**").hasAnyAuthority("Admin", "Editor")
	    .requestMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
	    .requestMatchers("/products/**").hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper")
	    .requestMatchers("/questions/**").hasAnyAuthority("Admin", "Assistant")
	    .requestMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant")
	    .requestMatchers("/customers/**").hasAnyAuthority("Admin", "Salesperson")
	    .requestMatchers("/shipping/**").hasAnyAuthority("Admin", "Salesperson")
	    .requestMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
	    .requestMatchers("/reports/**").hasAnyAuthority("Admin", "Salesperson")
	    .requestMatchers("/articles/**").hasAnyAuthority("Admin", "Editor")
	    .requestMatchers("/menus/**").hasAnyAuthority("Admin", "Editor")
	    .requestMatchers("/settings/**").hasAuthority("Admin")
	    .anyRequest().authenticated()
	    .and().formLogin().loginPage("/login").usernameParameter("email").permitAll()
	    .and().logout().permitAll()
	    .and().rememberMe().key("ABCDEFG").tokenValiditySeconds(24 * 60 * 60);
	return http.build(); 
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
	return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService())
		.passwordEncoder(passwordEncoder()).and().build();
    }
}
