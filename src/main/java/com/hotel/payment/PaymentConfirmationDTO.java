package com.hotel.payment;

import java.time.LocalDate;

import com.hotel.room.RoomType;

public class PaymentConfirmationDTO {

	private String transactionId;
	private Long bookingId;
	private String hotelName;
	private RoomType roomType;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double amount;
	private String paymentMethod;

	public static PaymentConfirmationDTO from(Payment payment) {
		PaymentConfirmationDTO dto = new PaymentConfirmationDTO();
		dto.setTransactionId(payment.getTransactionId());
		dto.setBookingId(payment.getBooking().getId());
		dto.setHotelName(payment.getBooking().getRoom().getHotel().getName());
		dto.setRoomType(payment.getBooking().getRoom().getType());
		dto.setCheckIn(payment.getBooking().getCheckIn());
		dto.setCheckOut(payment.getBooking().getCheckOut());
		dto.setAmount(payment.getAmount());
		dto.setPaymentMethod(payment.getPaymentMethod());
		return dto;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}
