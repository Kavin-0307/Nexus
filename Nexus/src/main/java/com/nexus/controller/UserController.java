package com.nexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.configuration.AuthService;
import com.nexus.dtos.AuthResponseDTO;
import com.nexus.dtos.LoginRequestDTO;
import com.nexus.dtos.RegisterRequestDTO;

import jakarta.validation.Valid;
/*I dont know whether i keep nexus/ or not*/
@RestController
@RequestMapping("/nexus/auth")
public class UserController {
	private final AuthService authService;
	public UserController(AuthService authService) {
		this.authService=authService;
	}
	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> createUser(@Valid@RequestBody RegisterRequestDTO dto){
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
	}
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid@RequestBody LoginRequestDTO dto){
		return ResponseEntity.status(HttpStatus.OK).body(authService.login(dto));
	}
	
	
}
