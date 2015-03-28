package br.com.spring.jpa.mvc.example.context;

import br.com.spring.jpa.mvc.example.interceptors.AuthHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"br.com.spring.jpa.mvc.example.controllers"})
public class SpringMVCContext extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthHandlerInterceptor authHandlerInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //definindo onde está as páginas
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    //com isso sua aplicação tem suporte a aceitar multipart type, tipo imagens, sons, etc.
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }

    //ativando nosso interceptor

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor)
                .addPathPatterns("/secure/**");

        super.addInterceptors(registry);
    }
}
