package com.app.photo.api.user.service;


import com.app.photo.api.user.data.UserEntity;
import com.app.photo.api.user.data.UserRepository;
import com.app.photo.api.user.model.AlbumResponseModel;
import com.app.photo.api.user.model.GetUserResponseModel;
import com.app.photo.api.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository repository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    private final Environment environment;

    @Autowired
    public UserServicesImpl(UserRepository repository,
                            ModelMapper modelMapper,
                            PasswordEncoder passwordEncoder,
                            RestTemplate restTemplate,
                            Environment environment) {

        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    @Override
    public UserDto addUser(UserDto dto) {
        dto.setUserId(UUID.randomUUID().toString());
        dto.setEncryptedPassword(passwordEncoder.encode(dto.getPassword()));

        UserEntity userEntity = modelMapper.map(dto, UserEntity.class);
        repository.save(userEntity);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userEntity" + userEntity.toString());

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getByEmail(String email) {
        UserEntity entity = repository.findByEmail(email);

        if(entity == null) {
            throw new UsernameNotFoundException(email);
        }

        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {

        UserEntity userEntity = repository.findByUserId(userId);

        if(userEntity == null){
            throw new UsernameNotFoundException(userId);
        }
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);


        String url = String.format(environment.getProperty("albums.getAllAlbumsByUserId"), userId);

        System.out.println(">>>>>>>>>>>>>" + url);

        ResponseEntity<List<AlbumResponseModel>> listAlbumResponse = restTemplate.exchange(
                url,
                HttpMethod.GET, null, 
                new ParameterizedTypeReference<List<AlbumResponseModel>>(){});

        List<AlbumResponseModel> listAlbums = listAlbumResponse.getBody();

        userDto.setAlbums(listAlbums);

        System.out.println(">>>>>>>>>>>>>" + userDto.toString());


        return userDto;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity entity = repository.findByEmail(email);

        if(entity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
