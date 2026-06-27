package com.nexus.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nexus.dtos.DocumentRequestDTO;
import com.nexus.dtos.DocumentResponseDTO;
import com.nexus.repository.DocumentRepository;
import com.nexus.repository.UserRepository;

import java.nio.file.Path;
import com.nexus.entity.Document;
import com.nexus.entity.User;
@Service
public class DocumentService {
	private final UserRepository userRepository;
	private final DocumentRepository documentRepository;
	private final FileStorageService fileStorageService;
	public DocumentService(FileStorageService fileStorageService,UserRepository userRepository,DocumentRepository documentRepository) {
		this.userRepository=userRepository;
		this.documentRepository=documentRepository;
		this.fileStorageService =fileStorageService;
	}
	public DocumentResponseDTO createDocument(MultipartFile file,DocumentRequestDTO dto,Long userId)  {
		User user=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("The user does not exist"));
		
		Document document=new Document();
		String extractedText = "";
		Path path;
		try {
		    path=fileStorageService.saveUploadedFile(file);

		    document.setFilePath(path.toString());

		    extractedText=fileStorageService.extractText(path);
		} catch(IOException e) {
		    throw new RuntimeException("File processing failed");
		}
		document.setFileSize(file.getSize());
		document.setOriginalFileName(file.getOriginalFilename());
		document.setUser(user);
		document.setDocumentType(dto.getDocumentType());
		document.setDocumentName(dto.getDocumentName());
		document.setExtractedText(extractedText);
		Document savedDocument = documentRepository.save(document);
		return convertToResponseDTO(savedDocument);
	}
	private DocumentResponseDTO convertToResponseDTO(Document doc) {
		return new DocumentResponseDTO(doc.getDocumentId(),
				doc.getDocumentName(),
				doc.getOriginalFileName(),
				doc.getDocumentType(),
				doc.getFileSize(),
				doc.getCreatedAt());
	}
	
	
	
}
