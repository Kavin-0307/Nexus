package com.nexus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.dtos.DocumentRequestDTO;
import com.nexus.dtos.DocumentResponseDTO;

import com.nexus.services.DocumentService;

@RestController
@RequestMapping("/nexus/document")
public class DocumentController {
	private final DocumentService documentService;
	public DocumentController(DocumentService documentService)
	{
		this.documentService=documentService;
	}
	@PostMapping	
	public ResponseEntity<DocumentResponseDTO> createDocument(@RequestBody DocumentRequestDTO dto,Authentication authentication){
		DocumentResponseDTO response=documentService.createDocument(dto,authentication.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	@GetMapping("/{documentId}")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable Long documentId,Authentication authentication){
		
		DocumentResponseDTO response=documentService.getDocumentById(documentId,authentication.getName());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	@PutMapping("/{documentId}")
	public ResponseEntity<DocumentResponseDTO> updateDocument(@RequestBody DocumentRequestDTO dto,Authentication authentication,@PathVariable Long documentId){
		DocumentResponseDTO response=documentService.updateDocument(documentId,dto,authentication.getName());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	@GetMapping()
	public ResponseEntity<List<DocumentResponseDTO>> getAllDocuments(Authentication authentication){
		return ResponseEntity.ok(documentService.getAllDocuments(authentication.getName()));
	}
	
	@DeleteMapping("/{documentId}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId,Authentication authentication){
		documentService.deleteDocument(documentId,authentication.getName());
        return ResponseEntity.noContent().build();

	}
	
}