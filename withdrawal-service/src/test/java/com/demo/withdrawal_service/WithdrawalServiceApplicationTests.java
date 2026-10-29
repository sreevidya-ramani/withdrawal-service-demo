package com.demo.withdrawal_service;

import com.demo.withdrawal_service.client.EligibilityClient;
import com.demo.withdrawal_service.client.EligibilityResponse;
import com.demo.withdrawal_service.controller.WithdrawalController;
import com.demo.withdrawal_service.service.WithdrawalService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WithdrawalController.class)
@Import(WithdrawalService.class)
class WithdrawalServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EligibilityClient eligibilityClient;

	@Test
	void approvesEligibleWithdrawal() throws Exception {
		when(eligibilityClient.checkEligibility(
				eq("P1001"), eq("STANDARD"), eq(new BigDecimal("125.00"))))
				.thenReturn(new EligibilityResponse(true, "Eligible"));

		mockMvc.perform(post("/api/v1/withdrawals")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"participantId":"P1001","withdrawalType":"STANDARD","amount":125.00,"status":"PENDING"}
							"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("APPROVED"))
				.andExpect(jsonPath("$.message").value("Withdrawal submitted"));
	}

	@Test
	void rejectsIneligibleWithdrawal() throws Exception {
		when(eligibilityClient.checkEligibility(
				eq("P1002"), eq("EARLY"), eq(new BigDecimal("250.00"))))
				.thenReturn(new EligibilityResponse(false, "Not eligible"));

		mockMvc.perform(post("/api/v1/withdrawals")
					.contentType(MediaType.APPLICATION_JSON)
					.content("""
							{"participantId":"P1002","withdrawalType":"EARLY","amount":250.00,"status":"PENDING"}
							"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("REJECTED"))
				.andExpect(jsonPath("$.message").value("Withdrawal not allowed"));
	}

}
