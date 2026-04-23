package com.hotel.payment;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentResultDTO processPayment(@RequestBody PaymentRequest request) {
		Long userId = getCurrentUserId();
		Payment payment = paymentService.processPayment(request.getBookingId(), request.getPaymentMethod(), userId);
		return PaymentResultDTO.from(payment);
	}

	@GetMapping("/{transactionId}")
	public PaymentConfirmationDTO getPaymentByTransactionId(@PathVariable String transactionId) {
		Long userId = getCurrentUserId();
		Payment payment = paymentService.getPaymentByTransactionIdForUser(transactionId, userId);
		return PaymentConfirmationDTO.from(payment);
	}

	private Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getName() == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authentication context");
		}

		try {
			return Long.parseLong(authentication.getName());
		}
		catch (NumberFormatException ex) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST,
				"JWT subject must be numeric userId to use payment endpoints"
			);
		}
	}

}
