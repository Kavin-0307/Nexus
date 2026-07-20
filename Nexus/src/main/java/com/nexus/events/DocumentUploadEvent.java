package com.nexus.events;

import java.nio.file.Path;

import com.nexus.enums.DocumentType;

public class DocumentUploadEvent {
	private final Long documentId;
	private final Long userId;
	private final DocumentType documentType;
	public DocumentUploadEvent(Long documentId,DocumentType documentType ,Long userId) {
		this.documentType=documentType;
		this.documentId=documentId;
		this.userId=userId;
	}
	public DocumentType getDocumentType() {
		return documentType;
		
	}
	public Long getDocumentId() {
		return documentId;
	}
	public Long getUserId() {
		return userId;
	}

}
