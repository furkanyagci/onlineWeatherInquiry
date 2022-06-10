package com.etiya.onlineWeatherInquiry.business.concretes;

import com.etiya.onlineWeatherInquiry.business.abstracts.UserService;
import com.etiya.onlineWeatherInquiry.business.constants.messages.BusinessMessages;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.CreateUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.DeleteUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.LoginUserRequest;
import com.etiya.onlineWeatherInquiry.business.requests.UserRequests.UpdateUserRequest;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.ListUserDto;
import com.etiya.onlineWeatherInquiry.business.responses.UserResponses.UserDto;
import com.etiya.onlineWeatherInquiry.core.crossCuttingConcerns.exceptionHandling.BusinessException;
import com.etiya.onlineWeatherInquiry.core.utilities.mapping.ModelMapperService;
import com.etiya.onlineWeatherInquiry.core.utilities.results.*;
import com.etiya.onlineWeatherInquiry.dataAccess.abstracts.UserRepository;
import com.etiya.onlineWeatherInquiry.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserService {
    private UserRepository userRepository;
    private ModelMapperService modelMapperService;

    public UserManager(UserRepository userRepository, ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ListUserDto>> getAll() {
        List<User> users = this.userRepository.findAll();
        List<ListUserDto> response = users.stream()
                .map(user -> this.modelMapperService.forDto().map(user,ListUserDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response,BusinessMessages.UserMessages.USER_LISTED);
    }

    @Override
    public DataResult<UserDto> getByUserName(String userName) {
        User user = this.userRepository.getByUserNameIgnoreCase(userName);
        if (user == null) {
            return new ErrorDataResult<>(BusinessMessages.UserMessages.USER_NOT_FOUND);
        }
        UserDto response = this.modelMapperService.forDto().map(user, UserDto.class);
        return new SuccessDataResult<>(response,BusinessMessages.UserMessages.USER_LISTED);
    }

    @Override
    public Result add(CreateUserRequest createUserRequest) {
        checkIfUserNameExists(createUserRequest.getUserName());
        User user =this.modelMapperService.forRequest().map(createUserRequest, User.class);
        user.setCreateDate(LocalDateTime.now());
        this.userRepository.save(user);
        return new SuccessDataResult<>(user.getId(), BusinessMessages.UserMessages.USER_ADDED);
    }

    @Override
    public Result delete(DeleteUserRequest deleteUserRequest) {
        this.userRepository.deleteById(deleteUserRequest.getId());
        return new SuccessResult(BusinessMessages.UserMessages.USER_DELETED);
    }

    @Override
    public Result update(UpdateUserRequest updateUserRequest,String userRole) {
        checkUserRole(updateUserRequest,userRole);
        User user =this.modelMapperService.forRequest().map(updateUserRequest, User.class);
        this.userRepository.save(user);
        return new SuccessResult(BusinessMessages.UserMessages.USER_UPDATED);
    }

    @Override
    public Result userLogin(LoginUserRequest loginUserRequest) {
        User result = this.userRepository.getByUserNameAndPasswordIgnoreCase(loginUserRequest.getUserName(),loginUserRequest.getPassword());
        if (result==null) {
            throw new BusinessException(BusinessMessages.UserMessages.LOGIN_FAILED);
        }
        UserDto loginUser =this.modelMapperService.forDto().map(result,UserDto.class);
        return new SuccessDataResult(loginUser,BusinessMessages.UserMessages.LOGIN_SUCCESSFUL);
    }

    private void checkUserRole(UpdateUserRequest updateUserRequest ,String userRole){
        if (userRole.equalsIgnoreCase("ADMIN") || userRole.equalsIgnoreCase("Admin")) {
            checkUserPasswordChange(updateUserRequest);
        }
    }

    private void checkUserPasswordChange(UpdateUserRequest updateUserRequest){
        Optional<User> user=this.userRepository.findById(updateUserRequest.getId());
        if (!updateUserRequest.getPassword().equals(user.get().getPassword())) {
            throw new BusinessException(BusinessMessages.UserMessages.NOT_AUTHORIZED);
        }
    }

    private void checkIfUserNameExists(String userName) {
        if (this.userRepository.existsByUserNameIgnoreCase(userName)) {
            throw new BusinessException(BusinessMessages.UserMessages.USERNAME_IS_USED);
        }
    }
}
