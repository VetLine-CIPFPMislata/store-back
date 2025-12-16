package org.example.storeback.Spring;

import org.example.storeback.controller.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private final AuthFilter authFilter;

    public WebConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        registration.setName("authFilter");
        return registration;
    }
}
