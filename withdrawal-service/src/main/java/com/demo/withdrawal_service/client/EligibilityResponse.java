package com.demo.withdrawal_service.client;

public record EligibilityResponse(boolean eligible, String reason) {
}