package com.aiden.trading.config;

import com.xxdb.SimpleDBConnectionPool;
import com.xxdb.SimpleDBConnectionPoolConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DolphindbConfig {
    @Value("${dolphindb.host_name}")
    private String hostName;
    @Value("${dolphindb.port}")
    private Integer port;
    @Value("${dolphindb.user_id}")
    private String userId;
    @Value("${dolphindb.password}")
    private String password;
    @Value("${dolphindb.initial_pool_size}")
    private Integer initialPoolSize;
    @Bean("dolphindbConnectionPool")
    public SimpleDBConnectionPool dolphindbConnectionPool() {
        SimpleDBConnectionPoolConfig config = new SimpleDBConnectionPoolConfig();
        config.setHostName(hostName);
        config.setPort(port);
        config.setUserId(userId);
        config.setPassword(password);
        config.setInitialPoolSize(initialPoolSize);
        config.setEnableHighAvailability(false);
        // 初始化连接池
        return new SimpleDBConnectionPool(config);
    }
}
