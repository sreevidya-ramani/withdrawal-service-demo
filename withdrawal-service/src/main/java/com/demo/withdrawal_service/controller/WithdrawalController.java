package com.demo.withdrawal_service.controller;

import com.demo.withdrawal_service.dto.WithdrawalRequest;
import com.demo.withdrawal_service.dto.WithdrawalResponse;
import com.demo.withdrawal_service.service.WithdrawalService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/withdrawals")
public class WithdrawalController {

	private final WithdrawalService withdrawalService;

	public WithdrawalController(WithdrawalService withdrawalService) {
		this.withdrawalService = withdrawalService;
	}

	@PostMapping
	public WithdrawalResponse submit(@RequestBody WithdrawalRequest request) {
		return withdrawalService.submit(request);
	}
}