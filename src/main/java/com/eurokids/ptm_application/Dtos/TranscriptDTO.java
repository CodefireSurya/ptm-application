package com.eurokids.ptm_application.Dtos;

import jakarta.validation.constraints.NotBlank;

public record TranscriptDTO(
        @NotBlank(message = "Meeting ID is required")
        String meetingId,

        @NotBlank(message = "Transcript cannot be empty")
        String transcript
) {}
