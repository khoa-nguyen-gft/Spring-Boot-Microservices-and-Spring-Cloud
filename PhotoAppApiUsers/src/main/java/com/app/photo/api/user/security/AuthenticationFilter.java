package com.app.photo.api.user.security;

import com.app.photo.api.user.model.LoginRequestModel;
import com.app.photo.api.user.service.UserServices;
import com.app.photo.api.user.shared.UserDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private  final Environment environment;

    private  final UserServices userServices;


    public AuthenticationFilter(AuthenticationManager authenticationManager, Environment environment, UserServices userServices) {
        super(authenticationManager);
        this.environment = environment;
        this.userServices = userServices;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String marshalledXml = org.apache.commons.io.IOUtils.toString(request.getInputStream());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        LoginRequestModel loginRequestModel = objectMapper.readValue(marshalledXml, LoginRequestModel.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        String email = principal.getUsername();
        UserDto userDto = userServices.getByEmail(email);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>userDto: " + userDto.toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>email: " + email );
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>environment.getProperty(\"token.expiration\"): " + environment.getProperty("token.expiration") );

        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(environment.getProperty("token.expiration"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>token: " + token );

        response.setHeader("token", token);
        response.setHeader("userId", userDto.getUserId());
    }
}
