package ru.skypro.homework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    //todo выяснить что и как делать с этим в условиях докера. Можно ли как-то выяснить в какой среде (контейнер или ОС) запущено приложение.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/content/**")
                .addResourceLocations("classpath:/images/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
