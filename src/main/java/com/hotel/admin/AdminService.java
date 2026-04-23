package com.hotel.admin;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.hotel.booking.Booking;
import com.hotel.booking.BookingRepository;
import com.hotel.booking.BookingStatus;
import com.hotel.hotel.HotelRepository;
import com.hotel.payment.Payment;
import com.hotel.payment.PaymentRepository;
import com.hotel.payment.PaymentStatus;
import com.hotel.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

	private final UserRepository userRepository;
	private final HotelRepository hotelRepository;
	private final BookingRepository bookingRepository;
	private final PaymentRepository paymentRepository;

	public AdminService(
		UserRepository userRepository,
		HotelRepository hotelRepository,
		BookingRepository bookingRepository,
		PaymentRepository paymentRepository
	) {
		this.userRepository = userRepository;
		this.hotelRepository = hotelRepository;
		this.bookingRepository = bookingRepository;
		this.paymentRepository = paymentRepository;
	}

	public AdminDashboardDTO getDashboard() {
		AdminDashboardDTO dto = new AdminDashboardDTO();
		dto.setTotalUsers(userRepository.count());
		dto.setTotalHotels(hotelRepository.count());
		dto.setTotalBookings(bookingRepository.count());
		dto.setConfirmedBookings(bookingRepository.countByStatus(BookingStatus.CONFIRMED));
		dto.setCancelledBookings(bookingRepository.countByStatus(BookingStatus.CANCELLED));
		dto.setTotalRevenue(paymentRepository.sumAmountByStatus(PaymentStatus.SUCCESS));
		dto.setRecentBookings(bookingRepository.findTop5ByOrderByCreatedAtDesc().stream()
			.map(this::toRecentBooking)
			.toList());
		return dto;
	}

	public List<AdminBookingDetailsDTO> getAllBookingDetails() {
		List<Booking> bookings = bookingRepository.findAllByOrderByCreatedAtDesc();
		List<Long> bookingIds = bookings.stream().map(Booking::getId).toList();
		Map<Long, Payment> paymentByBookingId = bookingIds.isEmpty()
			? Map.of()
			: paymentRepository.findByBookingIdIn(bookingIds).stream()
				.collect(Collectors.toMap(payment -> payment.getBooking().getId(), Function.identity()));

		return bookings.stream()
			.map(booking -> AdminBookingDetailsDTO.from(booking, paymentByBookingId.get(booking.getId())))
			.toList();
	}

	public List<AdminUserDTO> getAllUsers() {
		return userRepository.findAll().stream()
			.map(AdminUserDTO::from)
			.toList();
	}

	private RecentBookingDTO toRecentBooking(Booking booking) {
		RecentBookingDTO dto = new RecentBookingDTO();
		dto.setUserName(booking.getUser().getFullName());
		dto.setHotelName(booking.getRoom().getHotel().getName());
		dto.setRoomType(booking.getRoom().getType());
		dto.setCheckIn(booking.getCheckIn());
		dto.setCheckOut(booking.getCheckOut());
		dto.setStatus(booking.getStatus());
		dto.setAmount(booking.getTotalPrice());
		return dto;
	}

}
