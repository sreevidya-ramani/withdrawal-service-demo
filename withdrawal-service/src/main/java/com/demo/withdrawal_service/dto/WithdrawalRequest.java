package com.demo.withdrawal_service.dto;

import java.math.BigDecimal;

public record WithdrawalRequest(
		String participantId,
		String withdrawalType,
		BigDecimal amount,
		String status
) {
}