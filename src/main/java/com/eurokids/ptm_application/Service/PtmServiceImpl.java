package com.eurokids.ptm_application.Service;


import com.eurokids.ptm_application.Dtos.*;
import com.eurokids.ptm_application.Model.*;
import com.eurokids.ptm_application.Repository.EscalationsRepository;
import com.eurokids.ptm_application.Repository.MeetingsRepository;
import com.eurokids.ptm_application.Repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PtmServiceImpl implements PtmService {

    private final UserInfoRepository userInfoRepository;
    private final MeetingsRepository meetingsRepository;
    private final EscalationsRepository escalationsRepository;

    @Autowired
    public PtmServiceImpl(UserInfoRepository userInfoRepository, MeetingsRepository meetingsRepository,
                          EscalationsRepository escalationsRepository) {
        this.userInfoRepository = userInfoRepository;
        this.meetingsRepository = meetingsRepository;
        this.escalationsRepository = escalationsRepository;
    }

    @Override
    @Transactional
    public UserInfo createUserInfo(UserInfoDTO userInfoDTO) {
        if (userInfoRepository.existsByStaffId(userInfoDTO.staffId())) {
            throw new IllegalArgumentException("User with this staff ID already exists.");
        }
        UserInfo userInfo = userInfoDTO.toUserInfo();
        return userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public UserInfo setAvailabilitySlots(TimeSlotsDTO timeSlotsDTO) {
        Optional<UserInfo> userInfoOpt = userInfoRepository.findUserBasicDetails(timeSlotsDTO.userId());

        if (userInfoOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found for given ID.");
        }

        UserInfo userInfo = userInfoOpt.get();
        Availability availability = new Availability(timeSlotsDTO.startDate().toString(), getSlotDuration(timeSlotsDTO),
                Collections.singletonList(convertToTimeSlotEntity(timeSlotsDTO)));
        userInfo.getAvailability().add(availability);
        userInfo.setUpdatedAt(new Date());
        return userInfoRepository.save(userInfo);
    }

    private TimeSlots convertToTimeSlotEntity(TimeSlotsDTO timeSlotsDTO) {
        return new TimeSlots(timeSlotsDTO.startTime(), timeSlotsDTO.endTime(), timeSlotsDTO.status());
    }

    @Override
    @Transactional
    public Meetings scheduleMeetings(ScheduleMeetingsDTO scheduleMeetingsDTO) {
        if (!userInfoRepository.existsByStaffId(scheduleMeetingsDTO.staffId())) {
            throw new IllegalArgumentException("Staff ID not found.");
        }

        Meetings meeting = Meetings.builder()
                .staffId(scheduleMeetingsDTO.staffId())
                .parentId(scheduleMeetingsDTO.parentId())
                .staffEmail(scheduleMeetingsDTO.staffEmail())
                .parentEmail(scheduleMeetingsDTO.parentEmail())
                .date(scheduleMeetingsDTO.date().toString())
                .timeSlots(convertToTimeSlotEntity(scheduleMeetingsDTO.timeSlots()))
                .status("SCHEDULED")
                .notesApproved(Boolean.FALSE)
                .build();

        return meetingsRepository.save(meeting);
    }

    @Override
    public Meetings getMeetingDetails(String meetingId) {
        return meetingsRepository.findById(meetingId)
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found with ID: " + meetingId));
    }

    @Override
    @Transactional
    public Boolean cancelMeetings(String meetingId) {
        if (!meetingsRepository.existsById(meetingId)) {
            throw new IllegalArgumentException("Meeting not found.");
        }
        meetingsRepository.deleteById(meetingId);
        return true;
    }

    @Override
    public TimeSlotsDTO getTimeSlots(String staffId, String date) {
        List<Meetings> meetings = meetingsRepository.findByStaffIdAndDate(staffId, date);

        if (meetings.isEmpty()) {
            throw new IllegalArgumentException("No available slots for the given staff and date.");
        }

        Meetings meeting = meetings.get(0);
        return TimeSlotsDTO.fromEntity(meeting);
    }

    @Override
    @Transactional
    public Boolean saveTranscript(TranscriptDTO transcriptDTO) {
        Meetings meeting = meetingsRepository.findById(transcriptDTO.meetingId())
                .orElseThrow(() -> new IllegalArgumentException("Meeting not found."));

        meeting.setTranscript(transcriptDTO.transcript());
        meeting.setNotesApproved(false);

        meetingsRepository.save(meeting);
        return true;
    }

    @Override
    @Transactional
    public Escalations raiseEscalations(EscalationDTO escalationDTO) {
        if (!meetingsRepository.existsById(escalationDTO.meetingId())) {
            throw new IllegalArgumentException("Meeting ID does not exist.");
        }

        Escalations escalation = new Escalations(
                null,
                escalationDTO.meetingId(),
                escalationDTO.raisedBy(),
                escalationDTO.concern(),
                escalationDTO.status(),
                escalationDTO.staffId(),
                escalationDTO.handledBy()
        );

        return escalationsRepository.save(escalation);
    }

    private int getSlotDuration(TimeSlotsDTO timeSlotsDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(timeSlotsDTO.startTime(), formatter);
        LocalTime endTime = LocalTime.parse(timeSlotsDTO.endTime(), formatter);

        return (int) Duration.between(startTime, endTime).toMinutes();
    }
}
