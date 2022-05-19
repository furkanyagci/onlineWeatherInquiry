package com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineWeatherInquireDto {
    private MainDto main;
    private WeatherDto[] weather;
    private String name;
}
