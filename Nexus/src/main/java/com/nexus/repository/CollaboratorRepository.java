package com.nexus.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.entity.DocumentCollaborator;

public interface CollaboratorRepository extends JpaRepository<DocumentCollaborator,Long>{
	Optional<DocumentCollaborator> findByDocumentDocumentIdAndUserUserId(long documentId,long userId);
	List<DocumentCollaborator> findByDocumentDocumentId(long documentId);

    List<DocumentCollaborator> findByUserUserId(Long userId);
    void deleteByDocumentDocumentIdAndUserUserId(Long documentId,Long userId);
	boolean existsByDocumentDocumentIdAndUserUserId(Long documentId, Long userId);

	
}
