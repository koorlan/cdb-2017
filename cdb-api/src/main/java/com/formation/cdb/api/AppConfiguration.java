package com.formation.cdb.api;


import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.formation.cdb.persistence.HibernateConfiguration;


@Configuration
@EnableWebMvc
@ComponentScan({ "com.formation.cdb.api"})
@PropertySource(value = { "classpath:application.properties" })
@Import({ HibernateConfiguration.class })
@EnableTransactionManagement
public class AppConfiguration extends WebMvcConfigurerAdapter{

}
