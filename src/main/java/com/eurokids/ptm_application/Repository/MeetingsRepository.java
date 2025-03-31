package com.eurokids.ptm_application.Repository;

import com.eurokids.ptm_application.Model.Meetings;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingsRepository extends MongoRepository<Meetings, String> {

    @Aggregation(pipeline = {
            "{ $match: { 'staffId': ?0, 'date': ?1 } }",
            "{ $project: { 'timeSlotsDTO': 1, '_id': 0 } }"
    })
    List<Meetings> findAvailableTimeSlots(String staffId, String date);

    @Aggregation(pipeline = {
            "{ $match: { 'meetingId': ?0 } }",
            "{ $project: { 'meetingId': 1, 'parentId': 1, 'staffId': 1, 'status': 1, '_id': 0 } }"
    })
    Optional<Meetings> findMeetingSummary(String meetingId);

    List<Meetings> findByStaffIdAndDate(String staffId, String date);


}
