package com.etiya.onlineWeatherInquiry.business.requests.CityRequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequest {
    private String cityName;
    @JsonIgnore
    private LocalDateTime createDate;
}
