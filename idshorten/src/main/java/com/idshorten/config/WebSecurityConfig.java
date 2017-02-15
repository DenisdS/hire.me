package com.idshorten.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Http403ForbiddenEntryPoint forbiddenEntryPoint;

    @Bean
    public Http403ForbiddenEntryPoint forbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/**", "/shortener/id/v1/*", "/shortener/id/v1/console/*").permitAll()
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(forbiddenEntryPoint)
                .and()
                .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .and()
                .csrf().disable();
    }

    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}