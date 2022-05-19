package com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOnlineWeatherInquireDto {
    private String userName;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
}
