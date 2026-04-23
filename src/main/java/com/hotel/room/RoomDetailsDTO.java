package com.hotel.room;

public class RoomDetailsDTO {

	private Long id;
	private Long hotelId;
	private String hotelName;
	private String roomNumber;
	private RoomType type;
	private Double price;
	private int maxOccupancy;
	private boolean available;
	private String amenities;

	public static RoomDetailsDTO from(Room room) {
		RoomDetailsDTO dto = new RoomDetailsDTO();
		dto.setId(room.getId());
		dto.setHotelId(room.getHotel().getId());
		dto.setHotelName(room.getHotel().getName());
		dto.setRoomNumber(room.getRoomNumber());
		dto.setType(room.getType());
		dto.setPrice(room.getPrice());
		dto.setMaxOccupancy(room.getMaxOccupancy());
		dto.setAvailable(room.isAvailable());
		dto.setAmenities(room.getAmenities());
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

}
