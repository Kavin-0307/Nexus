package com.nexus.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nexus.dtos.AuthResponseDTO;
import com.nexus.dtos.LoginRequestDTO;
import com.nexus.dtos.RegisterRequestDTO;
import com.nexus.entity.User;
import com.nexus.repository.UserRepository;
import com.nexus.services.JwtService;

public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
		
	}
	public AuthResponseDTO register(RegisterRequestDTO dto) {
		if(userRepository.findByUserEmail(dto.getUserEmail()).isPresent()||userRepository.findByUserName(dto.getUserName()).isPresent()) {
			throw new IllegalArgumentException("User already exists");
			//this is temporary until i can create my own exception handling package
		}
		User user=new User();
		user.setUserName(dto.getUserName());
		user.setUserEmail(dto.getUserEmail());
		user.setUserPassword(passwordEncoder.encode(dto.getuserPassword()));
		user.setUserStatus(true);
		User saved=userRepository.save(user);
		String token = jwtService.generateToken(saved.getUserEmail());
		return convertToResponseDTO(saved,token);
		
	}
	public AuthResponseDTO login(LoginRequestDTO dto) {
		String identifier=dto.getIdentifier();
		  if (identifier == null) {
		        throw new IllegalArgumentException( "Identifier is required");
		    }

		User user;
		if(identifier.contains("@")) {
			user=userRepository.findByUserEmail(dto.getIdentifier()).orElseThrow(()->new IllegalArgumentException("Invalid Credentials"));
		}
		else {
			user=userRepository.findByUserName(identifier).orElseThrow(()->new IllegalArgumentException("Invalid Credentials"));
		}
		if (!passwordEncoder.matches(dto.getPassword(), user.getUserPassword()))
			throw new IllegalArgumentException("Invalid Credentials");
		//generate jwt somehow
		String token = jwtService.generateToken(user.getUserEmail());
		return convertToResponseDTO(user,token);
			
	}
	private AuthResponseDTO convertToResponseDTO(User user,String token) {
		return new AuthResponseDTO(user.getUserId(),user.getUserName(),user.getUserEmail(),token);
	}
	
}
