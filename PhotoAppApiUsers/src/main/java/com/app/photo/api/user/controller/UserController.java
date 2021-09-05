package com.app.photo.api.user.controller;


import com.app.photo.api.user.model.AddUserRequestModel;
import com.app.photo.api.user.model.AddUserResponseModel;
import com.app.photo.api.user.service.UserServices;
import com.app.photo.api.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return "Working on the port: " + env.getProperty("local.server.port");
    }


    @PostMapping
    public ResponseEntity<AddUserResponseModel> createUser(@Valid @RequestBody AddUserRequestModel request) {

        UserDto userDtoRequest = modelMapper.map(request, UserDto.class);

        UserDto userDtoResponse = userServices.addUser(userDtoRequest);

        AddUserResponseModel addUserResponseModel = modelMapper.map(userDtoResponse, AddUserResponseModel.class);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>addUserResponseModel" + addUserResponseModel.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(addUserResponseModel);
    }
}
