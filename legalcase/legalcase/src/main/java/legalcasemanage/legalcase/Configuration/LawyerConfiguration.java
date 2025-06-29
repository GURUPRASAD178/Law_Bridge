package legalcasemanage.legalcase.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import legalcasemanage.legalcase.repository.LawyerRepository;
import legalcasemanage.legalcase.service.CustomUserDetailsService;

@Configuration
public class LawyerConfiguration {

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;

    private final LawyerRepository lawyerRepository;
    

    public LawyerConfiguration(LawyerRepository lawyerRepository) {
        this.lawyerRepository = lawyerRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(lawyerRepository);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/about", "/contact", "/register", "/service", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/lawyer_dashboard/**").hasRole("LAWYER")
                .requestMatchers("/client_dashboard/**").hasRole("CLIENT")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
            	    .loginPage("/login")
            	    .loginProcessingUrl("/login")
            	    .successHandler(customLoginSuccessHandler) // Use handler here
            	    .permitAll()
            	)
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            )
            .build();
    }
}
