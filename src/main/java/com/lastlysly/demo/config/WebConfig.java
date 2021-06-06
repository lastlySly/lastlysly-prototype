package com.lastlysly.demo.config;

import com.google.common.collect.Lists;
import com.lastlysly.web.filter.ApiRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-06-06 15:49
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = Lists.newArrayList("/**");
        registry.addInterceptor(new ApiRequestInterceptor()).addPathPatterns(pathPatterns);
    }
}
