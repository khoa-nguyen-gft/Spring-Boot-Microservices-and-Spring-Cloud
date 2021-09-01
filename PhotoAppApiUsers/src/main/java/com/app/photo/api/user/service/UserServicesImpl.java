package com.app.photo.api.user.service;


import com.app.photo.api.user.data.UserEntity;
import com.app.photo.api.user.data.UserRepository;
import com.app.photo.api.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServicesImpl(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto addUser(UserDto dto) {
        dto.setUserId(UUID.randomUUID().toString());
        dto.setEncryptedPassword("123456");

        UserEntity userEntity = modelMapper.map(dto, UserEntity.class);
        repository.save(userEntity);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userEntity" + userEntity.toString());

        return modelMapper.map(userEntity, UserDto.class);
    }

}
