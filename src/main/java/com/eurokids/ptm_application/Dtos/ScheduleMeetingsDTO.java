package com.eurokids.ptm_application.Dtos;


import com.eurokids.ptm_application.Model.TimeSlots;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ScheduleMeetingsDTO(
        @NotBlank(message = "Staff ID is required")
        String staffId,

        @NotBlank(message = "Parent ID is required")
        String parentId,

        @Email(message = "Invalid staff email format")
        String staffEmail,

        @Email(message = "Invalid parent email format")
        String parentEmail,

        @Future(message = "Meeting date must be in the future")
        LocalDate date,

        @NotNull(message = "Time slot is required")
        TimeSlotsDTO timeSlots,

        String status,

        Boolean notesApproved
) {}
