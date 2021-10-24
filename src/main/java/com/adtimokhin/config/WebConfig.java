package com.adtimokhin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Properties;

/**
 * @author adtimokhin
 * 31.07.2021
 **/

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.adtimokhin")
public class WebConfig implements WebMvcConfigurer {


    /**
     * FreeMarkerViewResolver
     **/
    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver(){
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(".ftl"); // sets a suffix for all our ftl pages
        resolver.setPrefix(""); // tells Spring that there is no special prefix (beginning) for names of our ftl files
        resolver.setContentType("text/html;charset=UTF-8"); // Tells Spring that all of our ftl files are of type text/html
        resolver.setCache(false); // we don't need to cache our files
        resolver.setOrder(1); // sets order for this bean to be loaded into Spring Context

        return resolver;
    }

    /**
     * FreeMarkerConfigurer
     **/
    @Bean
    public FreeMarkerConfigurer getConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/templates"); // tells Spring where to find our ftl files
        configurer.setDefaultEncoding("UTF-8"); // states the encoding of our files
        configurer.setFreemarkerSettings(new Properties() {{ // Not sure what this line of code does different from the line above...
            this.put("default_encoding", "UTF-8");
        }});
        return configurer;
    }

    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
