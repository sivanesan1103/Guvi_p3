package com.hotel.search;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.hotel.booking.BookingRepository;
import com.hotel.booking.BookingStatus;
import com.hotel.hotel.Hotel;
import com.hotel.hotel.HotelRepository;
import com.hotel.room.Room;
import com.hotel.room.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchService {

	private final HotelRepository hotelRepository;
	private final RoomRepository roomRepository;
	private final BookingRepository bookingRepository;

	public SearchService(
		HotelRepository hotelRepository,
		RoomRepository roomRepository,
		BookingRepository bookingRepository
	) {
		this.hotelRepository = hotelRepository;
		this.roomRepository = roomRepository;
		this.bookingRepository = bookingRepository;
	}

	public List<SearchResultDTO> searchHotels(String city, LocalDate checkIn, LocalDate checkOut) {
		List<Hotel> hotels = hotelRepository.findByCity(city);

		return hotels.stream()
			.map(hotel -> mapHotelAvailability(hotel, checkIn, checkOut))
			.filter(result -> !result.getAvailableRooms().isEmpty())
			.toList();
	}

	private SearchResultDTO mapHotelAvailability(Hotel hotel, LocalDate checkIn, LocalDate checkOut) {
		List<Room> hotelAvailableRooms = roomRepository.findByHotelIdAndAvailable(hotel.getId(), true);
		List<Long> roomIds = hotelAvailableRooms.stream().map(Room::getId).toList();
		if (roomIds.isEmpty()) {
			SearchResultDTO dto = new SearchResultDTO();
			dto.setHotelId(hotel.getId());
			dto.setHotelName(hotel.getName());
			dto.setCity(hotel.getCity());
			dto.setRating(hotel.getRating());
			dto.setImageUrl(hotel.getImageUrl());
			return dto;
		}

		Set<Long> conflictingRoomIds = Set.copyOf(
			bookingRepository.findConflictingRoomIds(roomIds, checkIn, checkOut, BookingStatus.CONFIRMED)
		);

		List<RoomDTO> availableRooms = hotelAvailableRooms.stream()
			.filter(room -> !conflictingRoomIds.contains(room.getId()))
			.map(room -> new RoomDTO(room.getId(), room.getType(), room.getPrice(), room.getAmenities()))
			.toList();

		SearchResultDTO dto = new SearchResultDTO();
		dto.setHotelId(hotel.getId());
		dto.setHotelName(hotel.getName());
		dto.setCity(hotel.getCity());
		dto.setRating(hotel.getRating());
		dto.setImageUrl(hotel.getImageUrl());
		dto.setAvailableRooms(availableRooms);
		return dto;
	}

}
