package com.nexus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.dtos.CollaborationRequestDTO;
import com.nexus.dtos.CollaborationResponseDTO;
import com.nexus.services.CollaboratorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/nexus/collaboration")
public class CollaborationController {
	private final CollaboratorService collaboratorService;
	public CollaborationController(CollaboratorService collaboratorService) {
		this.collaboratorService=collaboratorService;
	}
	@PostMapping("/documents/{documentId}/collaborators")
	public ResponseEntity<CollaborationResponseDTO> inviteCollaborators(@PathVariable long documentId,@RequestBody@Valid CollaborationRequestDTO dto){
		CollaborationResponseDTO response=collaboratorService.inviteCollaborator(documentId,dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
