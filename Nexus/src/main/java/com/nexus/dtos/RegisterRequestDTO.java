package com.nexus.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequestDTO {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_email")
    private String userEmail;

    private String userPassword;
    public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail=userEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName=userName;
	}
	public String getuserPassword() {
		return userPassword;
	}
	public void setUserPassword(String password) {
		this.userPassword=password;
	}
}