package com.joga.app.authenticationserver;

import static org.mockito.Mockito.mockStatic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@ExtendWith(MockitoExtension.class)
class AuthenticationServerApplicationTest {
    @Test
    void test(){
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

            mocked.when(() -> SpringApplication.run(AuthenticationServerApplication.class,
                    "foo", "bar"))
                .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            AuthenticationServerApplication.main(new String[] { "foo", "bar" });

            mocked.verify(() -> { SpringApplication.run(AuthenticationServerApplication.class,
                "foo", "bar"); });

        }
    }

}