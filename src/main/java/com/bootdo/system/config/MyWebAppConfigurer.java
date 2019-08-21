package com.bootdo.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2019/6/24 0024.
 * @Description: 保存spring的ApplicationContext容器, 方便其他地方获取Bean
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Bean
    public SpringContextHolder springContextHolder(){
        return new SpringContextHolder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截规则：可以定义拦截的url和不拦截的url
        registry.addInterceptor(new UniqueUserInfoInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/docs/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/files/**")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/")
                .excludePathPatterns("/blog")
                .excludePathPatterns("/blog/open/**")
                .excludePathPatterns ("/css/**");
        super.addInterceptors(registry);
    }
}
