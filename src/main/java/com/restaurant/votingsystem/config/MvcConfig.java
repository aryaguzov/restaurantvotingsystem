package com.restaurant.votingsystem.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*
 * Handling an issue with Hibernate LazyInitializationException
 *
 * @link https://stackoverflow.com/a/21760361/15020481
 * */


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        MAPPER.registerModule(new Hibernate5Module());
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(new MediaType("application", "json", Charset.forName("UTF-8")));
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        messageConverter.setObjectMapper(MAPPER);
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
