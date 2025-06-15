package com.joga.app.authenticationserver;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
@ExtendWith(MockitoExtension.class)
class AuthenticationServerApplicationTest {
    @Test
    void testAuthorizationServerSecurityFilterChainDoesNotThrow() throws Exception {
        AuthorizationServerConfig config = new AuthorizationServerConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        assertDoesNotThrow(() -> config.authorizationServerSecurityFilterChain(httpSecurity));
    }
    @Test
    void testAuthorizationServerSettings() {
        AuthorizationServerConfig config = new AuthorizationServerConfig();
        ReflectionTestUtils.setField(config, "serverPort", "8080");

        AuthorizationServerSettings settings = config.authorizationServerSettings();
        assertTrue(settings.getIssuer().contains("8080"));
    }

    @Test
    void testRegisteredClientRepository() {
        AuthorizationServerConfig config = new AuthorizationServerConfig();
        ReflectionTestUtils.setField(config, "serverPort", "8080");

        RegisteredClientRepository repo = config.registeredClientRepository();
        assertNotNull(repo);
    }

    @Test
    void testDefaultSecurityFilterChainDoesNotThrow() throws Exception {
        AuthorizationServerConfig config = new AuthorizationServerConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);
        assertDoesNotThrow(() -> config.defaultSecurityFilterChain(httpSecurity));
    }
    @Test
    void testJwkSourceNotNull() {
        AuthorizationServerConfig config = new AuthorizationServerConfig();
        JWKSource<SecurityContext> jwkSource = config.jwkSource();
        assertNotNull(jwkSource);
    }


}