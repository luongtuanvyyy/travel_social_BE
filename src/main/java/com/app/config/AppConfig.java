package com.app.config;

import com.app.utils.CustomDateTimeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig implements WebMvcConfigurer {
    @Value("http://localhost:3000,http://localhost:3001,http://localhost:8080,http://127.0.0.1:3000/,http://127.0.0.1:5500/,http://127.0.0.1:3001,http://localhost:5173/")
    private String[] allowedOrigins;
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        long MAX_AGE_SECS = 60 * 60 * 24;
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return new CustomDateTimeProvider();
    }
}
