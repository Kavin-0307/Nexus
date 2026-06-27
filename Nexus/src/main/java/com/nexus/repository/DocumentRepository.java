package com.nexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexus.entity.Document;

public interface DocumentRepository extends JpaRepository<Document,Long>{

}
