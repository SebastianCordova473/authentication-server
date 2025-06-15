package com.joga.app.authenticationserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
@ExtendWith(MockitoExtension.class)
class ConfigServerDecryptClientTest {

    private ConfigServerDecryptClient client;
    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(is);
        }
        String decryptUrl = properties.getProperty("config.server.decrypt-url");

        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        client = new ConfigServerDecryptClient(restTemplate, decryptUrl) {
            @Override
            public String decrypt(String encryptedValue) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                HttpEntity<String> request = new HttpEntity<>(encryptedValue, headers);
                return restTemplate.postForObject(decryptUrl, request, String.class);
            }
        };
    }

    @Test
    void decrypt_shouldReturnDecryptedValue() {
        String input = "{cipher}secret";
        String expected = "my-password";

        mockServer.expect(once(), requestTo("http://localhost:8888/decrypt"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(expected, MediaType.TEXT_PLAIN));

        String actual = client.decrypt(input);

        assertEquals(expected, actual);
        mockServer.verify();
    }
}
