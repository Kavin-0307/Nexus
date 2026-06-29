package com.nexus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.entity.Document;

public interface DocumentRepository extends JpaRepository<Document,Long>{
	List<Document> findByUserUserId(Long userId);
	}
