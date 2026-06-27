package com.nexus.entity;
import jakarta.persistence.*;


import com.nexus.enums.DocumentType;

import java.time.LocalDateTime;

import org.hibernate.annotations.Generated;


@Entity
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long documentId;
	
	@Column(name="document_name")
	private String documentName;
	
	@Enumerated(EnumType.STRING)	
	@Column(name="document_type")
	private DocumentType documentType;
	
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Column(nullable=false)
	private String filePath;
	
	@Lob
	private String extractedText;
	
	@Column(nullable=false)
	private Long fileSize;
	
	private String originalFileName;
	
	
	@Column(nullable=false,updatable=false)
	private LocalDateTime createdAt;
	
	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	}
	
	public void setDocumentName(String documentName) {
		this.documentName=documentName;
	}
	public void setFilePath(String filePath)
	{
		this.filePath=filePath;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType=documentType;
	}
	public void setUser(User user) {
		this.user=user;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName=originalFileName;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	public User getUser() {
		return user;
	}
	
	public String getExtractedText() {
	    return extractedText;
	}

	public void setExtractedText(String extractedText) {
	    this.extractedText = extractedText;
	}
	public LocalDateTime getCreatedAt() {
	    return createdAt;
	}
	
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize=fileSize;
	}
	
	
}
