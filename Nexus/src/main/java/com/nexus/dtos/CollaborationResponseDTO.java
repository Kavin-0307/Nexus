package com.nexus.dtos;

import java.time.LocalDateTime;

import com.nexus.enums.CollaborationRole;

public record CollaborationResponseDTO (

    Long collaboratorId,
    Long userId,
    String userName,
    String userEmail,
    CollaborationRole collaborationRole,
    LocalDateTime addedAt

    ) {
}