package com.tek.pongservice.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rate-limiter")
public class RateLimiterProperties {

    /**
     * 每秒许可数
     */
    private double permitsPerSecond;

    /**
     * 预热时间（秒）
     */
    private long warmupPeriod;

    /**
     * 获取许可的超时时间（秒）
     */
    private long timeout;


    public double getPermitsPerSecond() {
        return permitsPerSecond;
    }

    public void setPermitsPerSecond(double permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    public long getWarmupPeriod() {
        return warmupPeriod;
    }

    public void setWarmupPeriod(long warmupPeriod) {
        this.warmupPeriod = warmupPeriod;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
