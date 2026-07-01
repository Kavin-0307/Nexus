package com.nexus.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
	
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	
	@Column(nullable=false,updatable=false)
	private LocalDateTime createdAt;
	
	
	@PrePersist
	protected void onCreate() {
	    createdAt = LocalDateTime.now();
	    updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
	    updatedAt = LocalDateTime.now();
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
