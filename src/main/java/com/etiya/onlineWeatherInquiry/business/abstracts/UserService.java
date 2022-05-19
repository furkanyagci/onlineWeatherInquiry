package com.etiya.onlineWeatherInquiry.business.abstracts;

import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.CreateUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.DeleteUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.LoginUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.UpdateUserRequest;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.ListUserDto;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.UserDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;

import java.util.List;

public interface UserService {

    DataResult<List<ListUserDto>> getAll();
    DataResult<UserDto> getByUserName(String userName);

    Result add(CreateUserRequest createUserRequest);
    Result delete(DeleteUserRequest deleteUserRequest);
    Result update(UpdateUserRequest updateUserRequest,String userRole);

    Result userLogin(LoginUserRequest loginUserRequest);
}
