package com.eurokids.ptm_application.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_info")
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends Auditable{

 @Id
 private String id;
 private String staffId;
 private String name;
 private String email;
 private List<Availability> availability;

 public UserInfo(String staffId, String name, String email, LocalDate startDate) {
  this.staffId = staffId;
  this.name = name;
  this.email = email;
  this.availability = getAvailability(startDate);
  this.setCreatedAt(new Date());
  this.setUpdatedAt(new Date());
 }

 private List<Availability> getAvailability(LocalDate date){
   return Collections.singletonList(new Availability(date));
 }
}