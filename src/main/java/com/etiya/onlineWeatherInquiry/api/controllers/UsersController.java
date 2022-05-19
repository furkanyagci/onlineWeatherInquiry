package com.etiya.onlineWeatherInquiry.api.controllers;

import com.etiya.onlineWeatherInquiry.business.abstracts.UserService;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.CreateUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.DeleteUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.LoginUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.UpdateUserRequest;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.ListUserDto;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.UserDto;
import com.etiya.onlineWeatherInquiry.core.utilities.results.DataResult;
import com.etiya.onlineWeatherInquiry.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController//
@RequestMapping("/api/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public DataResult<List<ListUserDto>> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/getbyusername")
    public DataResult<UserDto> getByUserName(String userName){
        return this.userService.getByUserName(userName);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateUserRequest createUserRequest) {
        return this.userService.add(createUserRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateUserRequest updateUserRequest,String userRole) {
        return this.userService.update(updateUserRequest,userRole);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody DeleteUserRequest deleteUserRequest) {
        return this.userService.delete(deleteUserRequest);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginUserRequest loginUserRequest) {
        return this.userService.userLogin(loginUserRequest);
    }//localstorage > FE
}
