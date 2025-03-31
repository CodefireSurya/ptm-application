package com.eurokids.ptm_application.Controller;

import com.eurokids.ptm_application.Dtos.*;
import com.eurokids.ptm_application.Model.Escalations;
import com.eurokids.ptm_application.Model.Meetings;
import com.eurokids.ptm_application.Model.UserInfo;
import com.eurokids.ptm_application.Service.PtmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ptm")
@Tag(name = "Parent Teacher Meeting Controller", description = "Manage Parent-Teacher Meetings")
public class PTMController {

    private final PtmService ptmService;

    public PTMController(PtmService ptmService) {
        this.ptmService = ptmService;
    }

    @Operation(summary = "Save user information", description = "Creates a new user (Teacher/Parent).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/users")
    public ResponseEntity<UserInfo> create(@RequestBody UserInfoDTO userInfoDTO) {
        return ResponseEntity.status(201).body(ptmService.createUserInfo(userInfoDTO));
    }

    @Operation(summary = "Set available time slots", description = "Allows teachers to set their available time slots.")
    @PostMapping("/slots")
    public ResponseEntity<UserInfo> saveSlots(@RequestBody TimeSlotsDTO timeSlotsDTO) {
        return ResponseEntity.ok(ptmService.setAvailabilitySlots(timeSlotsDTO));
    }

    @Operation(summary = "Get available slots", description = "Retrieves available time slots for a given teacher and date.")
    @GetMapping("/slots/{staffId}/{date}")
    public ResponseEntity<TimeSlotsDTO> getSlots(@PathVariable String staffId, @PathVariable String date) {
        return ResponseEntity.ok(ptmService.getTimeSlots(staffId, date));
    }

    @Operation(summary = "Schedule a meeting", description = "Schedules a one-on-one Parent-Teacher Meeting.")
    @PostMapping("/meetings/schedule")
    public ResponseEntity<?> scheduleMeeting(@RequestBody ScheduleMeetingsDTO scheduleMeetingsDTO) {
        return ResponseEntity.status(201).body(ptmService.scheduleMeetings(scheduleMeetingsDTO));
    }

    @Operation(summary = "Save meeting transcript", description = "Saves the transcript of a meeting after teacher approval.")
    @PutMapping("/meetings/transcript")
    public ResponseEntity<Boolean> saveTranscript(@RequestBody TranscriptDTO transcriptDTO) {
        return ResponseEntity.ok(ptmService.saveTranscript(transcriptDTO));
    }

    @Operation(summary = "Cancel a meeting", description = "Allows a parent or teacher to cancel a scheduled meeting.")
    @DeleteMapping("/meetings/{meetingId}/cancel")
    public ResponseEntity<Boolean> cancelMeetings(@PathVariable String meetingId) {
        return ResponseEntity.ok(ptmService.cancelMeetings(meetingId));
    }

    @Operation(summary = "Raise an escalation", description = "Parents can escalate issues related to a meeting.")
    @PostMapping("/escalations")
    public ResponseEntity<Escalations> raiseEscalations(@RequestBody EscalationDTO escalationDTO) {
        return ResponseEntity.ok(ptmService.raiseEscalations(escalationDTO));
    }

    @Operation(summary = "Get meeting details", description = "Fetches details of a scheduled meeting by its ID.")
    @GetMapping("/meetings/{meetingId}")
    public ResponseEntity<Meetings> getMeetingDetails(@PathVariable String meetingId) {
        return ResponseEntity.ok(ptmService.getMeetingDetails(meetingId));
    }
}
