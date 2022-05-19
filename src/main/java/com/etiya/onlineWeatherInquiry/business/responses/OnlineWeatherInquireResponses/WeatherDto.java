package com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private String main;
    private String description;
}
