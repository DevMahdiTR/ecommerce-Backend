package com.ecommerce.ecommerce.security.config;

import com.ecommerce.ecommerce.utility.cachesystem.GlobalLRUCache;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;

@Configuration
public class CacheConfig {
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GlobalLRUCache<String, Map<String, Object>> cacheSystem() {
        return GlobalLRUCache.getInstance();
    }
}
