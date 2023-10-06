package com.qianyu.springsecurity.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.qianyu.springsecurity.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Value("${secret.key}")
	private String secretKey;

	private static final String API_URL_PATTERN = "/**";
	
	@Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


	@Bean
	//authentication
	UserDetailsService userDetailsService() {

		return new CustomUserDetailsService();

	}


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http,
			HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

		http.csrf(csrfConfigurer ->
		csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN),
				toH2Console()));

		http.headers(headersConfigurer ->
		headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		http.authorizeHttpRequests(auth ->
		auth
		.requestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN)).permitAll()
		//This line is optional in .authenticated() case as .anyRequest().authenticated()
		//would be applied for H2 path anyway   
		.requestMatchers(toH2Console()).permitAll()
		.anyRequest().authenticated()
				)
		.oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));

		http.formLogin(Customizer.withDefaults());
		//http.httpBasic(Customizer.withDefaults());

		return http.build();
	}




	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}

	@Bean
	JwtEncoder jwtEncoder(){
		//String secretKey="9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";
		return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
	}
	
	@Bean
	JwtDecoder jwtDecoder(){
		//String secretKey="9faa372517ac1d389758d3750fc07acf00f542277f26fec1ce4593e93f64e338";
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
	}




}
