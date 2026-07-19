package com.skillswap.dto;

public class LoginResponse {


    private String token;
    private String type;
    private String name;
    private String email;
    
	public LoginResponse(String token, String type, String name, String email) {
		super();
		this.token = token;
		this.type = type;
		this.name = name;
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

    
    

}