package com.hotel.payment;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.hotel.booking.Booking;
import com.hotel.booking.BookingRepository;
import com.hotel.booking.BookingStatus;
import com.hotel.room.Room;
import com.hotel.room.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PaymentService {

	private static final Set<String> ALLOWED_PAYMENT_METHODS = Set.of("CARD", "UPI", "NETBANKING");

	private final PaymentRepository paymentRepository;
	private final BookingRepository bookingRepository;
	private final RoomRepository roomRepository;
	private final Random random = new Random();

	public PaymentService(
		PaymentRepository paymentRepository,
		BookingRepository bookingRepository,
		RoomRepository roomRepository
	) {
		this.paymentRepository = paymentRepository;
		this.bookingRepository = bookingRepository;
		this.roomRepository = roomRepository;
	}

	public Payment processPayment(Long bookingId, String paymentMethod) {
		return processPayment(bookingId, paymentMethod, null);
	}

	public Payment processPayment(Long bookingId, String paymentMethod, Long userId) {
		Booking booking = bookingRepository.findById(bookingId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

		if (userId != null && !booking.getUser().getId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only pay for your own booking");
		}

		if (paymentRepository.existsByBookingId(bookingId)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment already processed for this booking");
		}

		if (booking.getStatus() == BookingStatus.CANCELLED) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot pay for a cancelled booking");
		}

		String normalizedMethod = normalizePaymentMethod(paymentMethod);
		PaymentStatus paymentStatus = random.nextDouble() < 0.9 ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

		Payment payment = new Payment();
		payment.setBooking(booking);
		payment.setAmount(booking.getTotalPrice());
		payment.setPaymentMethod(normalizedMethod);
		payment.setStatus(paymentStatus);
		payment.setTransactionId(UUID.randomUUID().toString());
		Payment savedPayment = paymentRepository.save(payment);

		if (paymentStatus == PaymentStatus.FAILED) {
			booking.setStatus(BookingStatus.CANCELLED);
			Room room = booking.getRoom();
			room.setAvailable(true);
			roomRepository.save(room);
			bookingRepository.save(booking);
		}

		return savedPayment;
	}

	@Transactional(readOnly = true)
	public Payment getPaymentByTransactionIdForUser(String transactionId, Long userId) {
		Payment payment = paymentRepository.findByTransactionId(transactionId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
		if (!payment.getBooking().getUser().getId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only access your own payment");
		}
		return payment;
	}

	private String normalizePaymentMethod(String paymentMethod) {
		if (paymentMethod == null || paymentMethod.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentMethod is required");
		}
		String normalized = paymentMethod.trim().toUpperCase();
		if (!ALLOWED_PAYMENT_METHODS.contains(normalized)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentMethod must be CARD, UPI, or NETBANKING");
		}
		return normalized;
	}

}
