package com.etiya.onlineWeatherInquiry.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "weatherinquiryreports")
    public class WeatherInquiryReport {
    @Id
    private String id;
    private String userName;
    private String city;
    private String queryTime;
    private String ipAddress;
    private LocalDate queryDate;
}
