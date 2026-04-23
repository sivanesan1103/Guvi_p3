package com.hotel.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hotel.booking.Booking;
import com.hotel.booking.BookingStatus;
import com.hotel.payment.Payment;
import com.hotel.payment.PaymentStatus;
import com.hotel.room.RoomType;

public class AdminBookingDetailsDTO {

	private Long bookingId;
	private BookingStatus bookingStatus;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private Double totalPrice;
	private LocalDateTime createdAt;
	private Long userId;
	private String userName;
	private String userEmail;
	private Long hotelId;
	private String hotelName;
	private Long roomId;
	private String roomNumber;
	private RoomType roomType;
	private Double roomPrice;
	private String paymentMethod;
	private PaymentStatus paymentStatus;
	private String transactionId;
	private Double paymentAmount;
	private LocalDateTime paidAt;

	public static AdminBookingDetailsDTO from(Booking booking, Payment payment) {
		AdminBookingDetailsDTO dto = new AdminBookingDetailsDTO();
		dto.setBookingId(booking.getId());
		dto.setBookingStatus(booking.getStatus());
		dto.setCheckIn(booking.getCheckIn());
		dto.setCheckOut(booking.getCheckOut());
		dto.setTotalPrice(booking.getTotalPrice());
		dto.setCreatedAt(booking.getCreatedAt());

		dto.setUserId(booking.getUser().getId());
		dto.setUserName(booking.getUser().getFullName());
		dto.setUserEmail(booking.getUser().getEmail());

		dto.setHotelId(booking.getRoom().getHotel().getId());
		dto.setHotelName(booking.getRoom().getHotel().getName());
		dto.setRoomId(booking.getRoom().getId());
		dto.setRoomNumber(booking.getRoom().getRoomNumber());
		dto.setRoomType(booking.getRoom().getType());
		dto.setRoomPrice(booking.getRoom().getPrice());

		if (payment != null) {
			dto.setPaymentMethod(payment.getPaymentMethod());
			dto.setPaymentStatus(payment.getStatus());
			dto.setTransactionId(payment.getTransactionId());
			dto.setPaymentAmount(payment.getAmount());
			dto.setPaidAt(payment.getPaidAt());
		}

		return dto;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(Double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public LocalDateTime getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(LocalDateTime paidAt) {
		this.paidAt = paidAt;
	}

}
