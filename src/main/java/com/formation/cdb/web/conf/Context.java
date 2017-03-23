package com.formation.cdb.web.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.formation.cdb.formatter.CompanyDtoFormatter;


@Configuration
@EnableWebMvc
@ComponentScan("com.formation.cdb")
@EnableTransactionManagement
public class Context {
    
    @Bean 
    public FormattingConversionService formattingConversionService () {
        FormattingConversionService service = new FormattingConversionService();
        service.addFormatter(new CompanyDtoFormatter());
        return service;
    }
}
