package com.nexus.dtos;

import com.nexus.enums.CollaborationRole;

public class CollaborationRequestDTO {
	private String email;
	private CollaborationRole collaborationRole;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {this.email=email;}
	public void setCollaborationRole(CollaborationRole collaborationRole) {
		this.collaborationRole=collaborationRole;
	}
	public CollaborationRole getCollaborationRole() {
		return collaborationRole;
	}
}
