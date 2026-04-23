package com.hotel.payment;

public class PaymentResultDTO {

	private PaymentStatus status;
	private String transactionId;
	private Double amount;
	private Long bookingId;

	public static PaymentResultDTO from(Payment payment) {
		PaymentResultDTO dto = new PaymentResultDTO();
		dto.setStatus(payment.getStatus());
		dto.setTransactionId(payment.getTransactionId());
		dto.setAmount(payment.getAmount());
		dto.setBookingId(payment.getBooking().getId());
		return dto;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

}
