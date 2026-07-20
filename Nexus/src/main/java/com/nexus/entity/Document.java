package com.nexus.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.nexus.enums.DocumentType;
import com.nexus.enums.ProcessingStatus;
@Entity
@Table(name="documents")
public class Document {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long documentId;
	
	@Column(name="document_name")
	private String documentName;
	
	@Column(name="document_content",columnDefinition="TEXT")
	private String documentContent;
	
	@Column(name="document_updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="document_type")
	@Enumerated(EnumType.STRING)
	private DocumentType documentType;
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	
	@Column(nullable=false,updatable=false)
	private LocalDateTime createdAt;
	@Enumerated(EnumType.STRING)
	private ProcessingStatus processingStatus;
	
	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	    updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
	    updatedAt = LocalDateTime.now();
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType=documentType;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	public void setProcessingStatus(ProcessingStatus processingStatus) {
		this.processingStatus=processingStatus;
	}
	public ProcessingStatus getProcessingStatus() {
		return processingStatus;
	}
	public void setDocumentName(String documentName) {
		this.documentName=documentName;
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	public Long getDocumentId() {
		return documentId;
	}
	public String getDocumentName() {
		return documentName;
	}
	public User getUser() {
		return user;
	}
	
	public LocalDateTime getCreatedAt() {
	    return createdAt;
	}
	
	public void setDocumentContent(String documentContent) {
	    this.documentContent = documentContent;
	}

	public String getDocumentContent() {
	    return documentContent;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
	    this.updatedAt = updatedAt;
	}

	public LocalDateTime getUpdatedAt() {
	    return updatedAt;
	}
	
}
