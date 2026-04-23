package com.hotel.room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findByHotelId(Long hotelId);

	List<Room> findByHotelIdAndAvailable(Long hotelId, boolean available);

}
