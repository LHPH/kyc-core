package com.kyc.core.config;


import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class LoadSimpleSqlConfig {

    @Bean
    public PropertiesFactoryBean queriesProps(){

        PropertiesFactoryBean props = new PropertiesFactoryBean();
        props.setLocation(new ClassPathResource("sql/queries.xml"));
        return props;

    }
}
