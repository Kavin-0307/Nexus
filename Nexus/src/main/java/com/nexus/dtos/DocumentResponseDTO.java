package com.nexus.dtos;

import java.time.LocalDateTime;

public record DocumentResponseDTO(
        Long documentId,
        String documentName,
        String documentContent,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}