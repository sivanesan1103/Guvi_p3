package com.hotel.admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardDTO {

	private long totalUsers;
	private long totalHotels;
	private long totalBookings;
	private long confirmedBookings;
	private long cancelledBookings;
	private Double totalRevenue;
	private List<RecentBookingDTO> recentBookings = new ArrayList<>();

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalHotels() {
		return totalHotels;
	}

	public void setTotalHotels(long totalHotels) {
		this.totalHotels = totalHotels;
	}

	public long getTotalBookings() {
		return totalBookings;
	}

	public void setTotalBookings(long totalBookings) {
		this.totalBookings = totalBookings;
	}

	public long getConfirmedBookings() {
		return confirmedBookings;
	}

	public void setConfirmedBookings(long confirmedBookings) {
		this.confirmedBookings = confirmedBookings;
	}

	public long getCancelledBookings() {
		return cancelledBookings;
	}

	public void setCancelledBookings(long cancelledBookings) {
		this.cancelledBookings = cancelledBookings;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public List<RecentBookingDTO> getRecentBookings() {
		return recentBookings;
	}

	public void setRecentBookings(List<RecentBookingDTO> recentBookings) {
		this.recentBookings = recentBookings;
	}

}
