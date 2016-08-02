package com.ufril.config;

import com.amazonaws.sns.api.AmazonSNSService;
import com.amazonaws.sns.api.AmazonSNSServiceImpl;
import com.google.maps.api.GoogleMapApiClient;
import com.paypal.svcs.services.AdaptivePaymentsService;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactory;

import javax.servlet.MultipartConfigElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * @author Noman
 * 
 */
@Configuration
public class CommonConfig {

    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactory velocityEngineFactory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put(RuntimeConstants.RESOURCE_LOADER, "class");
        props.put("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngineFactory.setVelocityProperties(props);
        return velocityEngineFactory.createVelocityEngine();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    GoogleMapApiClient googleMapApiClient() {
        return new GoogleMapApiClient();
    }

    @Bean
    AdaptivePaymentsService adaptivePaymentsService() {
        return new AdaptivePaymentsService();
    }


    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("1MB");
        factory.setMaxRequestSize("5MB");
        return factory.createMultipartConfig();
    }
}
