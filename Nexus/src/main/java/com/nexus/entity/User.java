package com.nexus.entity;

import org.hibernate.annotations.Generated;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="user_email")
	private String userEmail;
	
	@Column(name="user_password")
	private String userPassword;
	@Column(name="is_active")
	private boolean isActive;
	
	public void setUserId(Long userId) {
		this.userId=userId;
	}
	public Long getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName=userName;
		
	}
	public void setUserEmail(String userEmail) {
		this.userEmail=userEmail;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public boolean isActive() {
		return isActive;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword=userPassword;
	}
	public void  setUserStatus(boolean b) {
		isActive=b;
	}
}
