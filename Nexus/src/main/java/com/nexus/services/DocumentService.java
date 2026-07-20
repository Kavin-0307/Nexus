package com.nexus.services;


import org.springframework.stereotype.Service;

import com.nexus.dtos.DocumentRequestDTO;
import com.nexus.dtos.DocumentResponseDTO;
import com.nexus.repository.CollaboratorRepository;
import com.nexus.repository.DocumentRepository;
import com.nexus.repository.UserRepository;

import java.util.List;
import java.util.stream.Stream;

import com.nexus.entity.Document;
import com.nexus.entity.User;
import com.nexus.enums.CollaborationRole;
@Service
public class DocumentService {
	private final UserRepository userRepository;
	private final CollaboratorRepository collaboratorRepository ;
	private final DocumentRepository documentRepository;
	public DocumentService(CollaboratorRepository collaboratorRepository,UserRepository userRepository,DocumentRepository documentRepository) {
		this.userRepository=userRepository;
		this.collaboratorRepository=collaboratorRepository;

		this.documentRepository=documentRepository;
	}
	public DocumentResponseDTO createDocument(DocumentRequestDTO dto,String email)  {
		
		User user=getUserByEmail(email);
		Document document=new Document();
		
		document.setUser(user);
		document.setDocumentName(dto.getDocumentName());
		document.setDocumentContent(dto.getDocumentContent());
		Document savedDocument = documentRepository.save(document);
		return convertToResponseDTO(savedDocument);
	}
	public List<DocumentResponseDTO> getAllDocuments(String email){
		User user=getUserByEmail(email);
		List<Document> owned=documentRepository.findByUserUserId(user.getUserId());
		List<Document> shared=collaboratorRepository.findByUserUserId(user.getUserId()).stream().map(c->c.getDocument()).toList();
		return Stream.concat(owned.stream(),shared.stream()).distinct().map(this:: convertToResponseDTO).toList();
	}
	//Maybe this can be used as the way to finally update the document when everything is closing down. It wont make sense to use this for the normal editor work
	public DocumentResponseDTO updateDocument(Long documentId,DocumentRequestDTO dto,String email) {
		User user=getUserByEmail(email);
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!canEdit(document,user)) {
	        throw new IllegalArgumentException("You are not authorized to edit this document");
	    }
	    document.setDocumentName(dto.getDocumentName());
	    document.setDocumentContent(dto.getDocumentContent());
	    Document updatedDocument =documentRepository.save(document);
	    return convertToResponseDTO(updatedDocument);
	}
	
	
	public DocumentResponseDTO getDocumentById(Long documentId,String email) {
		User user=getUserByEmail(email);
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!canView(document,user)) {
	        throw new IllegalArgumentException(
	                "You are not authorized to view this document");
	    }
	    return convertToResponseDTO(document);
	}
	
	public void deleteDocument(Long documentId, String email) {
		User user=getUserByEmail(email);
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!isOwner(document,user)) {
	        throw new IllegalArgumentException("You are not authorized to delete this document");
	    }
	    documentRepository.delete(document);
	}
	private DocumentResponseDTO convertToResponseDTO(Document doc) {

	    return new DocumentResponseDTO(
	            doc.getDocumentId(),
	            doc.getDocumentName(),
	            doc.getDocumentContent(),
	            doc.getCreatedAt(),
	            doc.getUpdatedAt()
	    );
	}
	private User getUserByEmail(String email) {
		return userRepository.findByUserEmail(email).orElseThrow(()->new IllegalArgumentException("User not found"));
	}
	private boolean isOwner(Document document,User user) {
		return document.getUser().getUserId().equals(user.getUserId());
		
	}
    private boolean isCollaborator(Document document, User user) {
        return collaboratorRepository.existsByDocumentDocumentIdAndUserUserId(
                document.getDocumentId(),
                user.getUserId());
    }

	private boolean canView(Document document ,User user) {
		return isOwner(document,user)||isCollaborator(document,user);
	}
	private boolean canEdit(Document document,User user) {
		if(isOwner(document,user))return true;
		return collaboratorRepository.findByDocumentDocumentIdAndUserUserId(document.getDocumentId(),user.getUserId()).map(c->c.getCollaborationRole()==CollaborationRole.EDITOR).orElse(false);
	}
	
	
}
