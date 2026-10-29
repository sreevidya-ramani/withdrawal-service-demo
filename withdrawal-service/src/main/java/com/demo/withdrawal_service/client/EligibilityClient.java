package com.demo.withdrawal_service.client;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EligibilityClient {

	private final RestClient restClient;

	public EligibilityClient(
			RestClient.Builder restClientBuilder,
			@Value("${eligibility.base-url}") String baseUrl
	) {
		this.restClient = restClientBuilder.baseUrl(baseUrl).build();
	}

	public EligibilityResponse checkEligibility(String participantId, String withdrawalType, BigDecimal amount) {
		return restClient.post()
				.uri("/api/v1/eligibility/check")
				.contentType(MediaType.APPLICATION_JSON)
				.body(new EligibilityCheckRequest(participantId, withdrawalType, amount))
				.retrieve()
				.body(EligibilityResponse.class);
	}

	private record EligibilityCheckRequest(String participantId, String withdrawalType, BigDecimal amount) {
	}
}