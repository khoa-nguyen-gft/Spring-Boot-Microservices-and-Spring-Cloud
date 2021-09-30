package com.app.photo.api.user.service;

import com.app.photo.api.user.model.GetUserResponseModel;
import com.app.photo.api.user.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServices extends UserDetailsService {

    public UserDto addUser(UserDto dto);

    public UserDto getByEmail(String email);

    public UserDto getUserById(String userId);

}


