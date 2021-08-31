package com.app.photo.api.user.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    //private long id;
    private String userId;
    private String fistName;
    private String lastName;
    private String password;
    private String encryptedPassword;
    private String email;
}
