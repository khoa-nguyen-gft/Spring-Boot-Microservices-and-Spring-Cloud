package com.app.photo.api.user.data;

import com.app.photo.api.user.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

    @GetMapping("/users/{userId}/albumss")
    public List<AlbumResponseModel> getAllAlbumsByUserId(@PathVariable String userId);

}
