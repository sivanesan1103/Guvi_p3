package com.hotel.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.hotel.room.Room;
import com.hotel.room.RoomRepository;
import com.hotel.user.User;
import com.hotel.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class BookingService {

	private final BookingRepository bookingRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	public BookingService(
		BookingRepository bookingRepository,
		RoomRepository roomRepository,
		UserRepository userRepository
	) {
		this.bookingRepository = bookingRepository;
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
	}

	public Booking createBooking(Long userId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
		validateDates(checkIn, checkOut);

		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		Room room = roomRepository.findById(roomId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

		if (bookingRepository.hasConflictingBooking(roomId, checkIn, checkOut)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Room already booked for selected dates");
		}

		long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
		double totalPrice = room.getPrice() * nights;

		Booking booking = new Booking();
		booking.setUser(user);
		booking.setRoom(room);
		booking.setCheckIn(checkIn);
		booking.setCheckOut(checkOut);
		booking.setTotalPrice(totalPrice);
		booking.setStatus(BookingStatus.CONFIRMED);

		room.setAvailable(false);
		roomRepository.save(room);
		return bookingRepository.save(booking);
	}

	public Booking cancelBooking(Long bookingId, Long userId) {
		Booking booking = bookingRepository.findById(bookingId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

		if (!booking.getUser().getId().equals(userId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only cancel your own booking");
		}

		booking.setStatus(BookingStatus.CANCELLED);
		Room room = booking.getRoom();
		room.setAvailable(true);
		roomRepository.save(room);

		return bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public List<Booking> getUserBookings(Long userId) {
		return bookingRepository.findByUserId(userId);
	}

	private void validateDates(LocalDate checkIn, LocalDate checkOut) {
		if (checkIn == null || checkOut == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkIn and checkOut are required");
		}
		if (!checkOut.isAfter(checkIn)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "checkOut must be after checkIn");
		}
	}

}
