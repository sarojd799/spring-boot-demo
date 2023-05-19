package  com.demoapp.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.demoapp.filters.JWTFilter;
import com.demoapp.services.UserDetailsUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Autowired
	private UserDetailsUtils userDetailsService;
	


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// `auth.inMemoryAuthentication().withUser("test").password("test").roles("USER");
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncorder());
	}


	
	/*-Note: To check the different types of password encoders-*/
	@Bean
	public PasswordEncoder getPasswordEncorder() {
		return new BCryptPasswordEncoder();
	}

	/*------------------------JDBC AUTHENTICATION-----------------------*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();

		http.authorizeRequests()
		   .antMatchers(
				   "/images/**",
				   "/login",
				   "/api/existsUserWithName",
				   "/registerNewUser",
				   "/secured/**","/secured/**/**","/secured/**/**/**", "/secured/socket", "/secured/success",
				   "/spring-security-mvc-socket/**",
				   "/api/getAllCountries",
				   "/api/{countryId}/getAllStatesOfCountry",
				   "/api/{stateId}/getAllCitiesOfState",
				   "/api/refreshToken",
				   "/swagger-resources/**",
	                "/swagger-ui.html",
	                "/v2/api-docs",
	                "/webjars/**",
	                "/topic","/topic/**","/registerForChat","/chatEvent/**",
	                "/actuator/**"
		    ).permitAll()
		   .anyRequest().authenticated()
		   .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	
}
