package com.formation.cdb.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Component
public class ViewResolver extends InternalResourceViewResolver{
    public ViewResolver () {
        this.setPrefix("/WEB-INF/views/jsp/");
        this.setSuffix(".jsp");
    }
}
