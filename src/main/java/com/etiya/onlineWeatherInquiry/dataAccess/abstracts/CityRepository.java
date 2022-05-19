package com.etiya.onlineWeatherInquiry.dataAccess.abstracts;

import com.etiya.onlineWeatherInquiry.entities.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City,String> {
    boolean existsByCityNameIgnoreCase(String cityName);
    City getByCityNameIgnoreCase(String cityName);
}
