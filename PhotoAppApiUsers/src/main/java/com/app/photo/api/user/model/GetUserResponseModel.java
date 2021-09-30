package com.app.photo.api.user.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponseModel {
    private String userId;
    private String fistName;
    private String lastName;
    private String email;
    private List<AlbumResponseModel> listAlbums;
}
