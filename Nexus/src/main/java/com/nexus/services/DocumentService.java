package com.nexus.services;


import org.springframework.stereotype.Service;

import com.nexus.dtos.DocumentRequestDTO;
import com.nexus.dtos.DocumentResponseDTO;
import com.nexus.repository.DocumentRepository;
import com.nexus.repository.UserRepository;

import java.util.List;

import com.nexus.entity.Document;
import com.nexus.entity.User;
@Service
public class DocumentService {
	private final UserRepository userRepository;
	private final DocumentRepository documentRepository;
	public DocumentService(UserRepository userRepository,DocumentRepository documentRepository) {
		this.userRepository=userRepository;
		this.documentRepository=documentRepository;
	}
	public DocumentResponseDTO createDocument(DocumentRequestDTO dto,Long userId)  {
		User user=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("The user does not exist"));
		
		Document document=new Document();
		
		document.setUser(user);
		document.setDocumentName(dto.getDocumentName());
		document.setDocumentContent(dto.getDocumentContent());
		Document savedDocument = documentRepository.save(document);
		return convertToResponseDTO(savedDocument);
	}
	public List<DocumentResponseDTO> getAllDocuments(Long userId){
		User user=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
		return documentRepository.findByUserUserId(userId).stream().map(this::convertToResponseDTO).toList();
		
	}
	//Maybe this can be used as the way to finally update the document when everything is closing down. It wont make sense to use this for the normal editor work
	public DocumentResponseDTO updateDocument(Long documentId,DocumentRequestDTO dto,Long userId) {
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!document.getUser().getUserId().equals(userId)) {
	        throw new IllegalArgumentException("You are not authorized to edit this document");
	    }
	    document.setDocumentName(dto.getDocumentName());
	    document.setDocumentContent(dto.getDocumentContent());
	    Document updatedDocument =documentRepository.save(document);
	    return convertToResponseDTO(updatedDocument);
	}
	
	
	public DocumentResponseDTO getDocumentById(Long documentId, Long userId) {
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!document.getUser().getUserId().equals(userId)) {
	        throw new IllegalArgumentException(
	                "You are not authorized to view this document");
	    }
	    return convertToResponseDTO(document);
	}
	
	public void deleteDocument(Long documentId, Long userId) {
	    Document document = documentRepository.findById(documentId).orElseThrow(()->new IllegalArgumentException("Document not found"));
	    if (!document.getUser().getUserId().equals(userId)) {
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
	
	
	
}
