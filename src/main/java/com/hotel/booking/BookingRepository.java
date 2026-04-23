package com.hotel.booking;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUserId(Long userId);

	long countByStatus(BookingStatus status);

	List<Booking> findTop5ByOrderByCreatedAtDesc();

	List<Booking> findAllByOrderByCreatedAtDesc();

	@Query("""
		select (count(b) > 0)
		from Booking b
		where b.room.id = :roomId
		  and b.checkIn < :checkOut
		  and b.checkOut > :checkIn
		  and b.status = com.hotel.booking.BookingStatus.CONFIRMED
		""")
	boolean hasConflictingBooking(
		@Param("roomId") Long roomId,
		@Param("checkIn") LocalDate checkIn,
		@Param("checkOut") LocalDate checkOut
	);

	@Query("""
		select distinct b.room.id
		from Booking b
		where b.room.id in :roomIds
		  and b.checkIn < :checkOut
		  and b.checkOut > :checkIn
		  and b.status = :status
		""")
	List<Long> findConflictingRoomIds(
		@Param("roomIds") Collection<Long> roomIds,
		@Param("checkIn") LocalDate checkIn,
		@Param("checkOut") LocalDate checkOut,
		@Param("status") BookingStatus status
	);

}
