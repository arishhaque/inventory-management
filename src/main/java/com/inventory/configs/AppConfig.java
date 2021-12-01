package com.inventory.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class AppConfig {

    private Server server;

    @Data
    public static class Server {
        private Servlet servlet;
        private String port;
        private String displayName;

        @Data
        public static class Servlet {
            private String contextPath;
        }
    }
}
