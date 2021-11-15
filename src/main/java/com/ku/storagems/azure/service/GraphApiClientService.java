package com.ku.storagems.azure.service;

import com.ku.storagems.azure.config.AzureAppConfiguration;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
final class GraphApiClientService {

    private final AzureAppConfiguration configuration;

    @Bean
    public GraphServiceClient graphServiceClientFromConfiguration() {
        return GraphServiceUtils.createGraphServiceClient(configuration.getClientId(),
                configuration.getClientSecret(),
                configuration.getTenantId());
    }
}
