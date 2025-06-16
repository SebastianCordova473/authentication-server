package com.joga.app.authenticationserver;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class BaseProperties {

    @Value("${security.domain.config.rest.connection-request-timeout}")
    private int connectionRequestTimeout;

    @Value("${security.domain.config.rest.connect-timeout}")
    private int connectTimeout;

    @Value("${security.domain.config.rest.socket-timeout}")
    private int socketTimeout;

    @Value("${security.domain.config.rest.max-conn-total}")
    private int maxConnTotal;

    @Value("${security.domain.config.rest.max-conn-per-route}")
    private int maxConnPerRoute;
}
