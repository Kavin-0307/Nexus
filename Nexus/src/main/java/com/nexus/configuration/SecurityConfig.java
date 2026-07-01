package com.nexus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nexus.services.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomUserDetailsService userDetailsService;
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,CustomUserDetailsService userDetailsService) {
		System.out.println("SECURITY CONFIG LOADED");
		this.userDetailsService=userDetailsService;
		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
		
		
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(userDetailsService);
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;
}
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		System.out.println("Custome filter chain");
		http.csrf(csrf->csrf.disable())
		.cors(cors->{})
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->auth.requestMatchers("/nexus/auth/register","/nexus/auth/login").
				permitAll().requestMatchers("/nexus/auth/**").permitAll()
		.anyRequest().authenticated())
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
	}
	@Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
        
        config.setAllowedOrigins(java.util.List.of(
            //To be filled i forgot the actual urls
        ));
        
        config.setAllowedMethods(java.util.List.of("*"));
        config.setAllowedHeaders(java.util.List.of("*"));
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
            new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
