package com.hotel.admin;

import com.hotel.user.User;

public class AdminUserDTO {

	private Long id;
	private String fullName;
	private String email;

	public static AdminUserDTO from(User user) {
		AdminUserDTO dto = new AdminUserDTO();
		dto.setId(user.getId());
		dto.setFullName(user.getFullName());
		dto.setEmail(user.getEmail());
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
