package com.etiya.onlineWeatherInquiry.business.requests.CityRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequest {
    private String cityName;
}
