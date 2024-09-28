package com.house.transport.security.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.transport.config.TestApplication;
import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(classes = TestApplication.class)
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerAsCustomerThenReturnAccessAndRefreshTokenSuccessfully() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer requestBody = new Customer(null, "testname", "testsurname", "testmail@example.com", "1234567890", "TestPass.123");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();
    }

    @Test
    public void registerAsMoverThenReturnAccessAndRefreshTokenSuccessfully() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Mover requestBody = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register/mover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody))
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();
    }

    @Test
    public void authenticateCustomerThenReturnAccessAndRefreshTokenSuccessfully() throws Exception{
        ObjectMapper customerMapper = new ObjectMapper();
        Customer registerRequestBody = new Customer(null, "testname", "testsurname", "testmail@example.com", "1234567890", "TestPass.123");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerMapper.writeValueAsString(registerRequestBody))
        ).andExpect(status().is2xxSuccessful());
        ObjectMapper authenticateMapper = new ObjectMapper();
        AuthRequest authRequestBody = new AuthRequest(registerRequestBody.getEmail(), registerRequestBody.getPassword());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authenticate/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authenticateMapper.writeValueAsString(authRequestBody))
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();
    }

    @Test
    public void authenticateMoverThenReturnAccessAndRefreshTokenSuccessfully() throws Exception{
        ObjectMapper moverMapper = new ObjectMapper();
        Mover registerRequestBody = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        mockMvc.perform(
                MockMvcRequestBuilders.post("/register/mover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moverMapper.writeValueAsString(registerRequestBody))
        ).andExpect(status().is2xxSuccessful());
        ObjectMapper authenticateMapper = new ObjectMapper();
        AuthRequest authRequestBody = new AuthRequest(registerRequestBody.getEmail(), registerRequestBody.getPassword());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/authenticate/mover")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authenticateMapper.writeValueAsString(authRequestBody))
                ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();
    }

    @Test
    public void sendRefreshTokenThenReturnNewAccessTokenSuccessfully() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Customer registerRequestBody = new Customer(null, "testname", "testsurname", "testmail@example.com", "1234567890", "TestPass.123");
        MvcResult authResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestBody))
        ).andExpect(status().is2xxSuccessful()).andReturn();
        String responseContent = authResponse.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String refreshToken = jsonNode.get("refreshToken").asText();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/refresh-token")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(refreshToken)
        ).andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*]").exists())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").isNotEmpty())
                .andReturn();
    }

    @Test
    public void logoutUserThenReturnLogoutSuccessfulMessage() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Customer registerRequestBody = new Customer(null, "testname", "testsurname", "testmail@example.com", "1234567890", "TestPass.123");
        MvcResult authResponse = mockMvc.perform(
                MockMvcRequestBuilders.post("/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestBody))
        ).andExpect(status().is2xxSuccessful()).andReturn();
        String responseContent = authResponse.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseContent);
        String refreshToken = jsonNode.get("refreshToken").asText();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/logoutuser")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(refreshToken)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void logoutUserThenReturnLogoutFailedMessage() throws Exception{
        String testToken = "unauthenticatedAndUnauthorizedTokenExample";
        mockMvc.perform(
                MockMvcRequestBuilders.post("/logoutuser")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(testToken)
        ).andExpect(status().is4xxClientError())
                .andReturn();
    }
}
