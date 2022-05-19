package com.etiya.onlineWeatherInquiry.business.responses.UserResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String userName;
    private String role;
}
