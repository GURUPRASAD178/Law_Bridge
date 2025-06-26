package legalcasemanage.legalcase.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import legalcasemanage.legalcase.repository.repository;
import legalcasemanage.legalcase.service.customuserdatailservice;



@Configuration
public class LegalConfiguration {
	private repository repo;
	
	public LegalConfiguration(repository repo) {
		super();
		this.repo = repo;
	}
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();	

}
	@Bean
	public UserDetailsService userdetailsservice() {
		return new customuserdatailservice(repo);
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordencoder());
		provider.setUserDetailsService(userdetailsservice());
		return provider;
		
	}@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/user").hasRole("USER")
						.requestMatchers("/user").hasAnyRole("USER","ADMIN")
						.requestMatchers("/admin").hasRole("ADMIN")
						
						.requestMatchers("/","/about","/contact","/register","/service","Photos/**","css/**","js/**").permitAll() //access these page without default login
						.anyRequest().authenticated())
				.formLogin(login -> login
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.permitAll())
				.logout(logout -> logout
						.permitAll())
				.build();
				}
	
	
}
