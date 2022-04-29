package com.shure.encrypt.config;

import com.shure.encrypt.plugin.QueryPluginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {

    @Bean
    public QueryPluginInterceptor getQueryPlugin() {
        return new QueryPluginInterceptor();
    }

}
