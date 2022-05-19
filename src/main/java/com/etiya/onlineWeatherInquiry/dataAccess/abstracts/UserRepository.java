package com.etiya.onlineWeatherInquiry.dataAccess.abstracts;

import com.etiya.onlineWeatherInquiry.entities.City;
import com.etiya.onlineWeatherInquiry.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User getByUserNameAndPasswordIgnoreCase(String userName, String password);
    User getByUserNameIgnoreCase(String userName);
}
