package com.nexus.dtos;

import java.time.LocalDateTime;

import com.nexus.enums.*;

public record DocumentResponseDTO(
        Long documentId,
        String documentName,
        DocumentType documentType,
        ProcessingStatus processingStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}