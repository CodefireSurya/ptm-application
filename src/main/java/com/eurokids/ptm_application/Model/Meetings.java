package com.eurokids.ptm_application.Model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "meetings")
@EqualsAndHashCode(callSuper = true)
public class Meetings extends Auditable {

    @Id
    String id;

    String staffId;

    String parentId;

    String staffEmail;

    String parentEmail;

    String date;

    TimeSlots timeSlots;

    String status;

    String transcript;

    Boolean notesApproved;


}
