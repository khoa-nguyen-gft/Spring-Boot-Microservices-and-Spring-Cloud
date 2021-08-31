package com.app.photo.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestModel {
    @NotNull(message = "The fist name cannot be null")
    @Size(min = 5, message = "the first name must not be less than 5 characters")
    private String fistName;

    @NotNull(message = "The last name cannot be null")
    @Size(min = 5, message = "the last name must not be less than 5 characters")
    private String lastName;

    @NotNull(message = "the password must not be null")
    @Size(min = 5, max = 16, message = "Password must be equal or greater than 5 characters and less than 16 characters")
    private String password;

    @Email
    @NotNull(message = "the email must not be null")
    private String email;
}
