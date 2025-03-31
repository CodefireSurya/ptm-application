package com.eurokids.ptm_application.Dtos;

import com.eurokids.ptm_application.Model.Meetings;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record TimeSlotsDTO(


        @NotNull(message = "Start Date is required")
        LocalDate startDate,

        @NotBlank(message = "Start time is required")
        @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Invalid start time format (HH:mm)")
        String startTime,

        @NotBlank(message = "End time is required")
        @Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Invalid end time format (HH:mm)")
        String endTime,

        @NotBlank(message = "Status is required")
        String status,

        @NotBlank(message = "User ID is required")
        String userId
) {

        public static TimeSlotsDTO fromEntity(Meetings meetings) {
                return new TimeSlotsDTO(
                        convertStringToDate(meetings.getDate()),
                        meetings.getTimeSlots().getStartTime(),
                        meetings.getTimeSlots().getEndTime(),
                        meetings.getTimeSlots().getStatus(),
                        ""
                );
        }

        private static LocalDate convertStringToDate(String dateStr) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(dateStr, formatter);
        }
}
