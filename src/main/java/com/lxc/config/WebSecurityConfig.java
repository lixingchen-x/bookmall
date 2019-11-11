package com.lxc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {

        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public CurrentCartMethodArgumentResolver currentCartMethodArgumentResolver() {

        return new CurrentCartMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(currentUserMethodArgumentResolver());
        argumentResolvers.add(currentCartMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}