package com.eurokids.ptm_application.Service;

import com.eurokids.ptm_application.Dtos.*;
import com.eurokids.ptm_application.Model.Escalations;
import com.eurokids.ptm_application.Model.Meetings;
import com.eurokids.ptm_application.Model.UserInfo;


public interface PtmService {

    UserInfo createUserInfo(UserInfoDTO userInfoDTO);
    UserInfo setAvailabilitySlots(TimeSlotsDTO timeSlotsDTO);

    Meetings scheduleMeetings(ScheduleMeetingsDTO scheduleMeetingsDTO);

    Meetings getMeetingDetails(String meetingId);

    Escalations raiseEscalations(EscalationDTO escalationDTO);

    Boolean cancelMeetings(String meetingId);

    TimeSlotsDTO getTimeSlots(String staffId, String date);

    Boolean saveTranscript(TranscriptDTO transcriptDTO);
}
