package com.nexus.events;

import java.nio.file.Path;

public class DocumentUploadEvent {
	private final Long documentId;
	private final Long userId;
	private final String filePath;
	public DocumentUploadEvent(Long documentId,String filePath ,Long userId) {
		this.filePath=filePath;
		this.documentId=documentId;
		this.userId=userId;
	}
	public String getFilePath() {
		return filePath;
		
	}
	public Long getDocumentId() {
		return documentId;
	}
	public Long getUserId() {
		return userId;
	}
	

}
