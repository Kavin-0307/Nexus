package com.nexus.dtos;

import jakarta.validation.constraints.NotBlank;
public class DocumentRequestDTO {
	@NotBlank
	private String documentName;
	
	@NotBlank
	private String documentContent;
	public void setDocumentName(String documentName) {
		this.documentName=documentName;
	}
	public void setDocumentContent(String documentContent) {
		this.documentContent=documentContent;
	}
	
	public String getDocumentContent() {
		return documentContent;
	}
	public String getDocumentName() {
		return documentName;
	}
	
	
}
