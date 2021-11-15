package com.ku.storagems.azure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "azure.app")
public class AzureAppConfiguration {

    private String clientId;
    private String clientSecret;
    private String tenantId;
    private String userId;
    private String searchPath;

}
