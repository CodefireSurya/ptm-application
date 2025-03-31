package com.eurokids.ptm_application.Model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Availability {

    String date;

    int slotDurationMinutes;

    List<TimeSlots> slots;

    public Availability(LocalDate date) {
        this.date = date.toString();
    }
}
