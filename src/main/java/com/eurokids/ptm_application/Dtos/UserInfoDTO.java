package com.eurokids.ptm_application.Dtos;

import com.eurokids.ptm_application.Model.UserInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserInfoDTO(
        @NotBlank(message = "Staff ID is required")
        String staffId,

        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email format")
        String email,

        LocalDate startDate

) {
    public UserInfoDTO(UserInfo userInfo) {
        this(userInfo.getStaffId(), userInfo.getName(), userInfo.getEmail(), null);
    }

    public UserInfo toUserInfo() {
        return new UserInfo(staffId, name, email, startDate);
    }
}
