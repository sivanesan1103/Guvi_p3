package com.hotel.hotel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

	private final HotelService hotelService;

	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	@GetMapping
	public List<Hotel> getAllHotels() {
		return hotelService.getAllHotels();
	}

	@GetMapping("/{id}")
	public Hotel getHotelById(@PathVariable Long id) {
		return hotelService.getHotelById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Hotel createHotel(@RequestBody Hotel hotel) {
		return hotelService.createHotel(hotel);
	}

	@PutMapping("/{id}")
	public Hotel updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
		return hotelService.updateHotel(id, hotel);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteHotel(@PathVariable Long id) {
		hotelService.deleteHotel(id);
	}

}
