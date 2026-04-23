package com.hotel.booking;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class BookingRequest {

	private Long roomId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate checkIn;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate checkOut;

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

}
