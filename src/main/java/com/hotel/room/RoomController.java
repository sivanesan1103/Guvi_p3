package com.hotel.room;

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
@RequestMapping("/api/rooms")
public class RoomController {

	private final RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping("/hotel/{hotelId}")
	public List<Room> getRoomsByHotel(@PathVariable Long hotelId) {
		return roomService.getRoomsByHotel(hotelId);
	}

	@GetMapping("/{id}")
	public RoomDetailsDTO getRoomById(@PathVariable Long id) {
		return RoomDetailsDTO.from(roomService.getRoomById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Room createRoom(@RequestBody Room room) {
		return roomService.createRoom(room);
	}

	@PutMapping("/{id}")
	public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
		return roomService.updateRoom(id, room);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRoom(@PathVariable Long id) {
		roomService.deleteRoom(id);
	}

}
