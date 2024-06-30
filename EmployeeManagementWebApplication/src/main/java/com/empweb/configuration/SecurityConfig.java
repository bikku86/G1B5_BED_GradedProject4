package com.empweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.empweb.serviceImplementation.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                               .password(passwordEncoder().encode("password"))
//                               .roles("USER")
//                               .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//    
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	
	public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	 public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	 }
	
	
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
					.requestMatchers("/","/home","/employees/**").permitAll()
					.requestMatchers("/roles/**","/users/**").hasAnyRole("ADMIN")
					.requestMatchers("/employees/**").hasAnyAuthority("ADMIN","USER")
					.requestMatchers(HttpMethod.POST,"employees").hasAuthority("ADMIN")
			        	.requestMatchers(HttpMethod.PUT,"employees").hasAuthority("ADMIN")
			        	.requestMatchers(HttpMethod.DELETE,"employees/*").hasAuthority("ADMIN")
					.anyRequest().authenticated()
					);
			
//			.httpBasic(httpBasic -> {})
			 //.authenticationManager(new CustomAuthenticationManager())
			
			
//			.formLogin(formLogin -> formLogin
//					.permitAll()
////					.defaultSuccessUrl("/home",true)
//					);

//				.formLogin((form) -> form
//						.loginPage("/login")
//						//.defaultSuccessUrl("/home",true)
//						.permitAll())
//				.logout((logout) -> logout
//						.logoutUrl("/logout")
//						.logoutSuccessUrl("/login?logout")
//						.permitAll());
//		
		
		
		
//.exceptionHandling((exceptions) -> exceptions
//						.accessDeniedPage("/403"));
		

		return http.build();
	}
	
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//        return auth;
//    }


}
