package com.eurokids.ptm_application.Model;



import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "escalations")
@EqualsAndHashCode(callSuper = true)
public class Escalations extends Auditable{

    @Id
    String id;

    String meetingId;

    String raisedBy;

    String concern;

    String status;

    String staffId;

    String handledBy;

}
