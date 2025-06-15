package com.joga.app.authenticationserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServerConfigTest {

    @Mock
    private SecurityUserProperties props;

    @Mock
    private ConfigServerDecryptClient decryptClient;

    @InjectMocks
    private AuthorizationServerConfig config;

    @Test
    void testUserDetailsServiceCreatesUserSuccessfully() {
        when(props.getName()).thenReturn("sebastian");
        when(props.getPassword()).thenReturn("fake-cipher");
        when(decryptClient.decrypt("{cipher:fake-cipher}")).thenReturn("plain-password");

        UserDetailsService uds = config.userDetailsService(props, decryptClient);

        UserDetails user = uds.loadUserByUsername("sebastian");

        assertNotNull(user);
        assertEquals("sebastian", user.getUsername());
        assertEquals("{noop}plain-password", user.getPassword());
    }
    @Test
    void testPropsManualBinding() throws IOException {
        MutablePropertySources sources = new MutablePropertySources();
        PropertySource<?> propertySource = new ResourcePropertySource("classpath:application.properties");
        sources.addLast(propertySource);

        ConfigurableEnvironment environment = new StandardEnvironment();
        sources.forEach(environment.getPropertySources()::addLast);

        Binder binder = Binder.get(environment);
        SecurityUserProperties props = binder.bind("app.security.user", SecurityUserProperties.class).get();

        assertEquals("sebastian", props.getName());
        assertEquals("{cipher:}edffa421ccb7195d5575954ad72a7a387102a2ce652965198baeaaf54046eecd", props.getPassword());
    }
}
