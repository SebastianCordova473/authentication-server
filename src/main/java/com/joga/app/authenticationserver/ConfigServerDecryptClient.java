package com.joga.app.authenticationserver;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConfigServerDecryptClient {

    private final RestTemplate restTemplate;

    @Value("${config.server.decrypt-url}")
    private String configServerUrl;

    public ConfigServerDecryptClient(@Qualifier("restTemplateGateway") RestTemplate restTemplate,
                                     @Value("${config.server.decrypt-url}") String configServerUrl) {
        this.restTemplate = restTemplate;
        this.configServerUrl = configServerUrl;
    }

    public String decrypt(String encryptedValue) {

        if (StringUtils.isBlank(encryptedValue)) {
            return encryptedValue;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> request = new HttpEntity<>(encryptedValue, headers);

        return restTemplate.postForObject(configServerUrl, request, String.class);
    }
}

