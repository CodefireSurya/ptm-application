package com.eurokids.ptm_application.Dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EscalationDTO(
        String id,

        @NotBlank(message = "Meeting ID cannot be blank")
        String meetingId,

        @NotBlank(message = "Raised by cannot be blank")
        String raisedBy,

        @NotBlank(message = "Concern must be specified")
        String concern,

        @NotNull(message = "Status cannot be null")
        String status,

        @NotBlank(message = "Staff ID is required")
        String staffId,

        String handledBy
) {}

