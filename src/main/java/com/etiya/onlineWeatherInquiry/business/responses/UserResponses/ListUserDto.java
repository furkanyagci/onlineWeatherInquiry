package com.etiya.onlineWeatherInquiry.business.responses.UserResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUserDto {
    private String id;
    private String userName;
    private String role;
}
