package com.nexus.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.nexus.configuration.AuthService;
import com.nexus.dtos.CollaborationRequestDTO;
import com.nexus.dtos.CollaborationResponseDTO;
import com.nexus.entity.Document;
import com.nexus.entity.DocumentCollaborator;
import com.nexus.entity.User;
import com.nexus.enums.CollaborationRole;
import com.nexus.repository.CollaboratorRepository;
import com.nexus.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.nexus.repository.DocumentRepository;


@Service 
public class CollaboratorService {
	private final CollaboratorRepository collaboratorRepository;
	
	private final UserRepository userRepository;
	private final DocumentRepository documentRepository;

	private final AuthService authService;
	public CollaboratorService(AuthService authService,DocumentRepository documentRepository,UserRepository userRepository,CollaboratorRepository collaboratorRepository) {
		this.collaboratorRepository=collaboratorRepository;
		this.userRepository=userRepository;
		this.documentRepository=documentRepository;
		this.authService=authService;
	}
	@Transactional
	public CollaborationResponseDTO inviteCollaborator(Long documentId,CollaborationRequestDTO dto) {
		Document document=documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document does not exist"));
		DocumentCollaborator documentCollaborator =new DocumentCollaborator();
		String newEmail=dto.getEmail();
		User user=authService.getCurrentUser();

		if (!document.getUser().getUserId().equals(user.getUserId())) {
		    throw new AccessDeniedException("Only the owner can invite collaborators");
		}
		
		User invitedUser=userRepository.findByUserEmail(newEmail).orElseThrow(()->new IllegalArgumentException("User not found"));
		
		if (user.getUserId().equals(invitedUser.getUserId())) {
		    throw new IllegalArgumentException("Owner cannot be added as collaborator");
		}
		CollaborationRole collaborationRole=dto.getCollaborationRole();
		documentCollaborator.setDocument(document);
		documentCollaborator.setUser(invitedUser);
		documentCollaborator.setCollaborationRole(collaborationRole);
		collaboratorRepository.save(documentCollaborator);
		return convertToResponseDTO(documentCollaborator);
		
	}
	private CollaborationResponseDTO convertToResponseDTO(DocumentCollaborator doc) {
		return new CollaborationResponseDTO(doc.getCollaboratorId(),doc.getUser().getUserId(),doc.getUser().getUserName(),doc.getUser().getUserEmail(),doc.getCollaborationRole(),doc.getAddedAt());
	}
	
}
