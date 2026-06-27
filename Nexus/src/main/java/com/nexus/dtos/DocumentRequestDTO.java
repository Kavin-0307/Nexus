package com.nexus.dtos;

import com.nexus.enums.DocumentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class DocumentRequestDTO {
	@NotBlank
	private String documentName;
	@NotNull
	private DocumentType documentType;
	
	
	public void setDocumentName(String documentName) {
		this.documentName=documentName;
	}
	
	public void setDocumentType(DocumentType documentType) {
		this.documentType=documentType;
	}
	
	public DocumentType getDocumentType() {
		return documentType;
	}
	public String getDocumentName() {
		return documentName;
	}
	
	
}
