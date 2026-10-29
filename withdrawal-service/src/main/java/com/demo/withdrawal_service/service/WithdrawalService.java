package com.demo.withdrawal_service.service;

import com.demo.withdrawal_service.client.EligibilityClient;
import com.demo.withdrawal_service.client.EligibilityResponse;
import com.demo.withdrawal_service.dto.WithdrawalRequest;
import com.demo.withdrawal_service.dto.WithdrawalResponse;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalService {

	private final EligibilityClient eligibilityClient;

	public WithdrawalService(EligibilityClient eligibilityClient) {
		this.eligibilityClient = eligibilityClient;
	}

	public WithdrawalResponse submit(WithdrawalRequest request) {
		EligibilityResponse eligibility = eligibilityClient.checkEligibility(
				request.participantId(),
				request.withdrawalType(),
				request.amount()
		);

		if (eligibility.eligible()) {
			return new WithdrawalResponse("APPROVED", "Withdrawal submitted");
		}
		return new WithdrawalResponse("REJECTED", "Withdrawal not allowed");
	}
}