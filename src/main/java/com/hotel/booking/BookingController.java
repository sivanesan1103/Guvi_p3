package com.hotel.booking;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookingResponse createBooking(@RequestBody BookingRequest request) {
		Long userId = getCurrentUserId();
		Booking booking = bookingService.createBooking(
			userId,
			request.getRoomId(),
			request.getCheckIn(),
			request.getCheckOut()
		);
		return BookingResponse.from(booking);
	}

	@GetMapping("/my")
	public List<BookingResponse> getMyBookings() {
		Long userId = getCurrentUserId();
		return bookingService.getUserBookings(userId).stream()
			.map(BookingResponse::from)
			.toList();
	}

	@PutMapping("/{id}/cancel")
	public BookingResponse cancelBooking(@PathVariable Long id) {
		Long userId = getCurrentUserId();
		Booking booking = bookingService.cancelBooking(id, userId);
		return BookingResponse.from(booking);
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
				"JWT subject must be numeric userId to use booking endpoints"
			);
		}
	}

}
