package com.joga.app.authenticationserver;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConfigurationApplicationTest {

    @Test
    void restTemplateGatewayShouldReturnRestTemplate() {
        BaseProperties mockProps = mock(BaseProperties.class);
        when(mockProps.getConnectTimeout()).thenReturn(3000);
        when(mockProps.getSocketTimeout()).thenReturn(3000);
        when(mockProps.getConnectionRequestTimeout()).thenReturn(3000);
        when(mockProps.getMaxConnTotal()).thenReturn(100);
        when(mockProps.getMaxConnPerRoute()).thenReturn(20);

        ConfigurationApplication config = new ConfigurationApplication(mockProps);
        RestTemplate restTemplate = config.restTemplateGateway();

        assertNotNull(restTemplate);
    }
}