package com.nexus.dtos;

import java.time.LocalDateTime;

import com.nexus.enums.DocumentType;

public record DocumentResponseDTO(
        Long documentId,
        String documentName,
        String originalFileName,
        DocumentType documentType,
        Long fileSize,
        LocalDateTime createdAt
) {}