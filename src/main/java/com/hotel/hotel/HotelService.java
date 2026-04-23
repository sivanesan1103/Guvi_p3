package com.hotel.hotel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class HotelService {

	private final HotelRepository hotelRepository;

	public HotelService(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Transactional(readOnly = true)
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Hotel getHotelById(Long id) {
		return hotelRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
	}

	public Hotel createHotel(Hotel hotel) {
		hotel.setId(null);
		return hotelRepository.save(hotel);
	}

	public Hotel updateHotel(Long id, Hotel updatedHotel) {
		Hotel existingHotel = getHotelById(id);
		existingHotel.setName(updatedHotel.getName());
		existingHotel.setCity(updatedHotel.getCity());
		existingHotel.setAddress(updatedHotel.getAddress());
		existingHotel.setDescription(updatedHotel.getDescription());
		existingHotel.setRating(updatedHotel.getRating());
		existingHotel.setImageUrl(updatedHotel.getImageUrl());
		return hotelRepository.save(existingHotel);
	}

	public void deleteHotel(Long id) {
		Hotel existingHotel = getHotelById(id);
		hotelRepository.delete(existingHotel);
	}

}
