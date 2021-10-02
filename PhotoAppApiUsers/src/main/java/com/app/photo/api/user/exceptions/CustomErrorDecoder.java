package com.app.photo.api.user.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Configuration
public class CustomErrorDecoder implements ErrorDecoder {

    private final Environment environment;

    @Autowired
    public CustomErrorDecoder(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Exception decode(String s, Response response) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>s" + s);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>response" + response.toString());
        switch (response.status()){
            case 400:
                return new BadHttpRequest();
            case 404:{
                if(s.contains("getAllAlbumsByUserId")){
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                            environment.getProperty("albums.exceptions.albums-not-found"));
                }
                break;
            }
            default:
                return new Exception();
        }
        return null;
    }
}
