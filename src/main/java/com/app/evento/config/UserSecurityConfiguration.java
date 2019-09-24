package com.app.evento.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/")
        	.permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .logout()
            .permitAll();

        http.csrf().disable();
    }

	  @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {	     
	        auth.inMemoryAuthentication()
	                .withUser("user").password("{noop}password").roles("USER")
	                .and()
	                .withUser("admin").password("{noop}password").roles("ADMIN");

	    }
    
    }


