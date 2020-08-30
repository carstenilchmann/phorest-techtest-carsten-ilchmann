package com.example.dto;

import java.util.UUID;

public class TopClient {
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String gender;
	private Long totalLoyaltyPoints;

	public TopClient() {
	}
	
	public TopClient(UUID id, String firstName, String lastName, String email, String phone, String gender, Long totalLoyaltyPoints) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.totalLoyaltyPoints = totalLoyaltyPoints;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getTotalLoyaltyPoints() {
		return totalLoyaltyPoints;
	}

	public void setTotalLoyaltyPoints(Long totalLoyaltyPoints) {
		this.totalLoyaltyPoints = totalLoyaltyPoints;
	}

}
