package com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest;

import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.MainDto;
import com.etiya.onlineWeatherInquiry.business.responses.OnlineWeatherInquireResponses.WeatherDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchOnlineWeatherInquireRequest {
    private MainDto main;
    private WeatherDto[] weather;
    private String name;
    private long queryTime;
}
