package me.tianjx98.blog.config;

import me.tianjx98.blog.component.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.MultipartConfigElement;

/**
 * @ClassName WebAppConfig
 * @Author tianjx98
 * @Date 2019-09-24 15:30
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //未登录时拦截
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/new")      //拦截新增操作
                .addPathPatterns("/edit/**");           //拦截修改操作
    }

    //@Bean
    //public MultipartConfigElement multipartConfigElement() {
    //    MultipartConfigFactory factory = new MultipartConfigFactory();
    //    return factory.createMultipartConfig();
    //}
    //
    //@Bean
    //public InternalResourceViewResolver viewResolver() {
    //    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    //    resolver.setPrefix("file:" + staticPath);
    //    System.out.println(staticPath);
    //    resolver.setSuffix(".html");
    //    return resolver;
    //}

}
