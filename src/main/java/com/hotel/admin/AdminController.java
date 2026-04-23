package com.hotel.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/dashboard")
	public AdminDashboardDTO getDashboard() {
		return adminService.getDashboard();
	}

	@GetMapping("/bookings")
	public List<AdminBookingDetailsDTO> getAllBookings() {
		return adminService.getAllBookingDetails();
	}

	@GetMapping("/users")
	public List<AdminUserDTO> getAllUsers() {
		return adminService.getAllUsers();
	}

}
