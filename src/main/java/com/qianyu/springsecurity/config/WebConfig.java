package com.qianyu.springsecurity.config;


import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {
	
	@Bean
	FilterRegistrationBean corsFilter() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		// Allows backend to receive the headers which contains auth info
		config.setAllowCredentials(true);
		
		// Set frontend URL
		config.addAllowedOrigin("http://localhost:4200");
		
		// Typical headers that the backend should accept
		config.setAllowedHeaders(Arrays.asList(
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT			
				));
		
		// Methods that backend should accept
		config.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.PUT.name(),
				HttpMethod.POST.name(),
				HttpMethod.DELETE.name()
				));
		
		// Max 30min
		config.setMaxAge(3600L);
		
		// Apply all above configs to all my routes
		source.registerCorsConfiguration("/**", config);
		
		// Put this bean at the lowest position to be executed before any Spring beans
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		
		bean.setOrder(-102);
		return bean;
		
		
	}

}
