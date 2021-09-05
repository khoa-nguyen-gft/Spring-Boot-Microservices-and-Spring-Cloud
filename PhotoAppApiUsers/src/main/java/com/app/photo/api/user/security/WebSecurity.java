package com.app.photo.api.user.security;

import com.app.photo.api.user.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    private final UserServices userServices;

    private final PasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public WebSecurity(Environment environment, UserServices userServices, PasswordEncoder bCryptPasswordEncoder) {
        this.environment = environment;
        this.userServices = userServices;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String apigateWay = environment.getProperty("api-gateway.ip");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>configure: HttpSecurity: " + apigateWay);

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/**")
                .hasIpAddress(apigateWay);
        http.addFilter(getAuthenitcationfilter());
        http.headers().frameOptions().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServices).passwordEncoder(bCryptPasswordEncoder);



        super.configure(auth);
    }

    private AuthenticationFilter getAuthenitcationfilter() throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), environment, userServices);

//        authenticationFilter.setFilterProcessesUrl(environment.getProperty("app.config.security-url"));
        return authenticationFilter;

    }
}
