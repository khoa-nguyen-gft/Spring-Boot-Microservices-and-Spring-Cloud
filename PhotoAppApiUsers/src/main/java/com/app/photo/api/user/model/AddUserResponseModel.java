package com.app.photo.api.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddUserResponseModel {
    private String userId;
    private String fistName;
    private String lastName;
    private String email;
}
