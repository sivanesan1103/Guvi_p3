package com.hotel.user;

public class AuthResponse {

	private String token;
	private String role;
	private String name;
	private String email;
	private Long userId;

	public static AuthResponse from(User user, String token) {
		AuthResponse response = new AuthResponse();
		response.setToken(token);
		response.setRole(user.getRole().name());
		response.setName(user.getFullName());
		response.setEmail(user.getEmail());
		response.setUserId(user.getId());
		return response;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
