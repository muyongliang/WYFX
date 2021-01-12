/*
package com.wyfx.upms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

*/
/**
 * Created by liu on 2017/7/21.
 *//*

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"com.wyfx.upms.controller"},useDefaultFilters = false, includeFilters = @ComponentScan.Filter(value = {Controller.class, ControllerAdvice.class}))
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean characterEncodingFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registrationBean.setFilter(characterEncodingFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean mvcDispatcherRegistration() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.setApplicationContext(applicationContext);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/");
        servletRegistrationBean.setName("springMvc");
        return servletRegistrationBean;
    }
    protected MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);


        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd HH:mm:ss");
        dateFormatter.setLenient(true);
        registry.addFormatter(dateFormatter);
    }
}
*/
