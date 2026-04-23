package com.hotel.search;

import com.hotel.room.RoomType;

public class RoomDTO {

	private Long id;
	private RoomType type;
	private Double price;
	private String amenities;

	public RoomDTO() {
	}

	public RoomDTO(Long id, RoomType type, Double price, String amenities) {
		this.id = id;
		this.type = type;
		this.price = price;
		this.amenities = amenities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoomType getType() {
		return type;
	}

	public void setType(RoomType type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

}
