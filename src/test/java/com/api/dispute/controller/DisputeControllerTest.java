package com.api.dispute.controller;

import com.api.dispute.config.SecurityDisabledConfig;
import com.api.dispute.model.Dispute;
import com.api.dispute.service.DisputeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DisputeController.class)
@Import(SecurityDisabledConfig.class)
@ContextConfiguration(classes = { DisputeController.class, SecurityDisabledConfig.class })
class DisputeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisputeService disputeService;

    @Test
    @WithMockUser(username = "testuser")
    void testCreateDispute() throws Exception {
        Dispute dispute = new Dispute();
        dispute.setId(10L);
        dispute.setReason("Unauthorized transaction");

        Mockito.when(disputeService.createDispute("testuser", 100L, "Unauthorized transaction"))
                .thenReturn(dispute);

        mockMvc.perform(post("/api/disputes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "transactionId": "100",
                        "reason": "Unauthorized transaction"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.reason").value("Unauthorized transaction"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testGetUserDisputes() throws Exception {
        Dispute d1 = new Dispute(); d1.setId(1L);
        Dispute d2 = new Dispute(); d2.setId(2L);

        Mockito.when(disputeService.getDisputesByCurrentUser("testuser"))
                .thenReturn(Arrays.asList(d1, d2));

        mockMvc.perform(get("/api/disputes/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    @WithMockUser(username = "anyuser")  // Still works — endpoint doesn't use username
    void testGetDisputeById() throws Exception {
        Dispute dispute = new Dispute();
        dispute.setId(5L);
        dispute.setReason("Card not present fraud");

        Mockito.when(disputeService.getDisputeById(5L))
                .thenReturn(dispute);

        mockMvc.perform(get("/api/disputes/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.reason").value("Card not present fraud"));
    }
}