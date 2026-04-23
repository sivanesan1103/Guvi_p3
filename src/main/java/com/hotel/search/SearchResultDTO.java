package com.hotel.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDTO {

	private Long hotelId;
	private String hotelName;
	private String city;
	private Double rating;
	private String imageUrl;
	private List<RoomDTO> availableRooms = new ArrayList<>();

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<RoomDTO> getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(List<RoomDTO> availableRooms) {
		this.availableRooms = availableRooms;
	}

}
