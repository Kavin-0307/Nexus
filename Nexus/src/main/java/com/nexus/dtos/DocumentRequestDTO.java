package com.nexus.dtos;

import com.nexus.enums.DocumentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class DocumentRequestDTO {
	@NotBlank
	private String documentName;
	@NotNull
	DocumentType documentType;
	@NotBlank
	private String documentContent;
	
	public void setDocumentName(String documentName) {
		this.documentName=documentName;
	}
	public void setDocumentContent(String documentContent) {
		this.documentContent=documentContent;
	}
	public void setDocumentType(DocumentType documentType) {
		this.documentType=documentType;
	}
	public String getDocumentContent() {
		return documentContent;
	}
	public String getDocumentName() {
		return documentName;
	}
	public DocumentType getDocumentType() {
		return documentType;
	}
	
	
}
