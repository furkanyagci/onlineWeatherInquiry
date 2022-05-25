package com.etiya.onlineWeatherInquiry.business.requests.UserRequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String userName;

    private String password;

    private String role;

    @JsonIgnore
    private LocalDateTime createDate;
}
