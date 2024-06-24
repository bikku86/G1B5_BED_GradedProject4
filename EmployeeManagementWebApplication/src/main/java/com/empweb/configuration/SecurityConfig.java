package com.empweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new CustomUserDetailsService();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//	return new BCryptPasswordEncoder();
//	}
	
	
	
//
//	@Autowired
//	private CustomAuthenticationProvider customAuthenticationProvider;
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(customAuthenticationProvider);
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf
					.disable()
					)
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers("/","/h2-console/").permitAll()
					.requestMatchers("/roles/**","/users/**","/employees/**").hasRole("ADMIN")
					.requestMatchers("/users/**").hasRole("USER")
		            .requestMatchers(HttpMethod.POST,"employees").hasAuthority("ADMIN")
		            .requestMatchers(HttpMethod.PUT,"employees").hasAuthority("ADMIN")
		            .requestMatchers(HttpMethod.DELETE,"employees/*").hasAuthority("ADMIN")
					.anyRequest().authenticated()
					)
			
			//.httpBasic(withDefaults())
			 //.authenticationManager(new CustomAuthenticationManager())

				.formLogin((form) -> form
						.loginPage("/login")
						//.defaultSuccessUrl("/home",true)
						.permitAll())
				.logout((logout) -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.permitAll());
//.exceptionHandling((exceptions) -> exceptions
//						.accessDeniedPage("/403"));
		

		return http.build();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return auth;
    }


}
