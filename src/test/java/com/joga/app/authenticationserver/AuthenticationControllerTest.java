package com.joga.app.authenticationserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
@Import(TestSecurityConfig.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void callback_shouldReturnCodeReceived() throws Exception {
        mockMvc.perform(get("/login/oauth2")
                        .param("code", "abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Code Received: abc123"));
    }
}
