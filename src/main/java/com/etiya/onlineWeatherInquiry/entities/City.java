package com.etiya.onlineWeatherInquiry.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cities")
public class City {
    @Id
    private String id;
    private String cityName;
    private LocalDateTime createDate;
}
