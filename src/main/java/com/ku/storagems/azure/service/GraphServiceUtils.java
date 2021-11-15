package com.ku.storagems.azure.service;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

final class GraphServiceUtils {

    private GraphServiceUtils() {
    }

    public static GraphServiceClient createGraphServiceClient(String clientId,
                                                              String clientSecret,
                                                              String tenantId) {
        return GraphServiceClient
                .builder()
                .authenticationProvider(createTokenCredentialAuthProvider(clientId, clientSecret, tenantId))
                .buildClient();
    }

    private static TokenCredentialAuthProvider createTokenCredentialAuthProvider(String clientId,
                                                                                 String clientSecret,
                                                                                 String tenantId) {
        return new TokenCredentialAuthProvider(
                new ClientSecretCredentialBuilder()
                        .clientId(clientId)
                        .clientSecret(clientSecret)
                        .tenantId(tenantId)
                        .build()
        );
    }
}
