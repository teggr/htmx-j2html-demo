package com.robintegg.htmx.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public ViewResolver j2htmlViewResolver() {
    return new BeanNameViewResolver();
  }

}
