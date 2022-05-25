package com.etiya.onlineWeatherInquiry.business.responses.CityResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private String id;
    private String cityName;
    private LocalDateTime createDate;
}
