package com.app.photo.api.user.controller;


import com.app.photo.api.user.model.CreateUserRequestModel;
import com.app.photo.api.user.service.UserServices;
import com.app.photo.api.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserServices userServices;

    private ModelMapper modelMapper;


    @Autowired
    public UserController(UserServices userServices, ModelMapper modelMapper) {
        this.userServices = userServices;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on the port" + env.getProperty("local.server.port");
    }


    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRequestModel request) {

        UserDto userDto = modelMapper.map(request, UserDto.class);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userDto" + userDto.toString());

        userServices.addUser(userDto);


        return "create user method is call";
    }
}
