package com.hotel.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {

	private Long id;
	private Long userId;
	private Long roomId;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double totalPrice;
	private BookingStatus status;
	private LocalDateTime createdAt;

	public static BookingResponse from(Booking booking) {
		BookingResponse response = new BookingResponse();
		response.setId(booking.getId());
		response.setUserId(booking.getUser().getId());
		response.setRoomId(booking.getRoom().getId());
		response.setCheckIn(booking.getCheckIn());
		response.setCheckOut(booking.getCheckOut());
		response.setTotalPrice(booking.getTotalPrice());
		response.setStatus(booking.getStatus());
		response.setCreatedAt(booking.getCreatedAt());
		return response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
