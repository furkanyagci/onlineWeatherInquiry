package com.etiya.onlineWeatherInquiry.business.requests.OnlineWeatherInquireRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOnlineWeatherInquireRequest {
    private String userName;
    private String city;
    private long queryTime;
    private String ipAddress;
    private LocalDate queryDate;

}
