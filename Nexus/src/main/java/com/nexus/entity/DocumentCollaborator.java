package com.nexus.entity;
import java.time.LocalDateTime;

import com.nexus.enums.CollaborationRole;

import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames= {"document_id","user_id"}))
public class DocumentCollaborator {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long collaboratorId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="document_id",nullable=false)
	private Document document;
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role",nullable=false)
	private CollaborationRole collaborationRole;
	@Column(nullable = false, updatable = false)
	private LocalDateTime addedAt;

	@PrePersist
	public void onCreate() {
	    addedAt = LocalDateTime.now();
	}
	public Long getCollaboratorId() {return collaboratorId;}
	public User getUser() {return user;}
	public void setUser(User user) {this.user=user;}
	public void setCollaborationRole(CollaborationRole collaborationRole) {
		this.collaborationRole=collaborationRole;
	}
	public CollaborationRole getCollaborationRole() {return collaborationRole;}
	public Document getDocument(){return document;}

	public void setDocument(Document document){this.document=document;}

	public LocalDateTime getAddedAt() {
	    return addedAt;
	}
	
	
}
