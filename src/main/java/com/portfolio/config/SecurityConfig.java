package com.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private CoustomLogoutHandler coustomLogoutHandler;

	@Autowired
	public void setCoustomLogoutHandler(CoustomLogoutHandler coustomLogoutHandler) {
		this.coustomLogoutHandler = coustomLogoutHandler;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// PasswordEncoderFactories is the class in which the encoding logic is saved
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();

	}

	@Bean
	SecurityFilterChain sercurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				authorize -> authorize.requestMatchers("/download/**").permitAll().requestMatchers("/admin/**").authenticated()
				.requestMatchers("/**").permitAll())
				.formLogin(form -> form.loginPage("/login") // custom Login page method
						.permitAll())
				.logout(logout -> logout
						.addLogoutHandler(coustomLogoutHandler) //if we this we need custom message
						.logoutUrl("/dologout"));
		return httpSecurity.build();
	}
}
