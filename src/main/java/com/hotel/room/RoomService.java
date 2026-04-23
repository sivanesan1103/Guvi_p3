package com.hotel.room;

import java.util.List;

import com.hotel.hotel.Hotel;
import com.hotel.hotel.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class RoomService {

	private final RoomRepository roomRepository;
	private final HotelRepository hotelRepository;

	public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
		this.roomRepository = roomRepository;
		this.hotelRepository = hotelRepository;
	}

	@Transactional(readOnly = true)
	public List<Room> getRoomsByHotel(Long hotelId) {
		return roomRepository.findByHotelId(hotelId);
	}

	@Transactional(readOnly = true)
	public Room getRoomById(Long id) {
		return roomRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
	}

	@Transactional(readOnly = true)
	public List<Room> getAvailableRoomsByHotel(Long hotelId) {
		return roomRepository.findByHotelIdAndAvailable(hotelId, true);
	}

	public Room createRoom(Room room) {
		Long hotelId = extractHotelId(room);
		if (hotelId == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hotel.id is required");
		}
		Hotel hotel = getHotelOrThrow(hotelId);
		room.setId(null);
		room.setHotel(hotel);
		return roomRepository.save(room);
	}

	public Room updateRoom(Long id, Room updatedRoom) {
		Room existingRoom = roomRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

		existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
		existingRoom.setType(updatedRoom.getType());
		existingRoom.setPrice(updatedRoom.getPrice());
		existingRoom.setMaxOccupancy(updatedRoom.getMaxOccupancy());
		existingRoom.setAvailable(updatedRoom.isAvailable());
		existingRoom.setAmenities(updatedRoom.getAmenities());

		Long hotelId = extractHotelId(updatedRoom);
		if (hotelId != null) {
			existingRoom.setHotel(getHotelOrThrow(hotelId));
		}

		return roomRepository.save(existingRoom);
	}

	public void deleteRoom(Long id) {
		Room room = roomRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
		roomRepository.delete(room);
	}

	private Hotel getHotelOrThrow(Long hotelId) {
		return hotelRepository.findById(hotelId)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
	}

	private Long extractHotelId(Room room) {
		if (room.getHotel() == null) {
			return null;
		}
		return room.getHotel().getId();
	}

}
