package com.tek.pongservice.config;

import com.google.common.util.concurrent.RateLimiter;
import com.tek.pongservice.properties.RateLimiterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RateLimiterConfig {

    /**
     * 配置 RateLimiter Bean
     *
     * @param properties 注入的限流配置属性
     * @return RateLimiter 实例
     */
    @Bean
    public RateLimiter rateLimiter(RateLimiterProperties properties) {
        if (properties.getWarmupPeriod() > 0) {
            // 创建带有预热期的 RateLimiter
            return RateLimiter.create(
                    properties.getPermitsPerSecond(),
                    properties.getWarmupPeriod(),
                    TimeUnit.SECONDS
            );
        } else {
            // 创建标准的 RateLimiter
            return RateLimiter.create(properties.getPermitsPerSecond());
        }
    }
}
