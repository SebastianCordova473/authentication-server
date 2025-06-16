package com.joga.app.authenticationserver;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ConfigurationApplication {

    private final BaseProperties props;

    @Bean(name = "restTemplateGateway")
    public RestTemplate restTemplateGateway() {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(props.getConnectTimeout()))
                .setSocketTimeout(Timeout.ofMilliseconds(props.getSocketTimeout()))
                .build();

        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(props.getMaxConnTotal())
                .setMaxConnPerRoute(props.getMaxConnPerRoute())
                .setDefaultConnectionConfig(connectionConfig)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(props.getConnectionRequestTimeout());
        requestFactory.setConnectTimeout(props.getConnectTimeout());
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }

}
