package com.etiya.onlineWeatherInquiry.dataAccess.abstracts;

import com.etiya.onlineWeatherInquiry.entities.WeatherInquiryReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface WeatherInquiryReportRepository extends MongoRepository<WeatherInquiryReport,String> {
    List<WeatherInquiryReport> getByUserNameIgnoreCase(String userName);
    List<WeatherInquiryReport> getByCityIgnoreCase(String city);
    List<WeatherInquiryReport> getByUserNameIgnoreCaseAndQueryDateBetween(String userName, LocalDate startDate, LocalDate endDate);
    List<WeatherInquiryReport> getByCityIgnoreCaseAndQueryDateBetween(String city, LocalDate startDate, LocalDate endDate);
    List<WeatherInquiryReport> getByCityIgnoreCaseAndUserNameIgnoreCaseAndQueryDateBetween(String city, String userName, LocalDate startDate, LocalDate endDate);


}
