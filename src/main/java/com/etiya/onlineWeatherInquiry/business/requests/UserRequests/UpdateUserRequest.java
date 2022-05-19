package com.etiya.onlineWeatherInquiry.business.requests.UserRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String id;

    private String userName;

    private String password;

    private String role;
}
