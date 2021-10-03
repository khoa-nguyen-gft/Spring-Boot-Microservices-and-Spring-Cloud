package com.app.photo.api.user.data;

import com.app.photo.api.user.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws", fallbackFactory= AlbumsFallBackFactory.class)
public interface AlbumsServiceClient {

    @GetMapping("/users/{userId}/albumss")
    public List<AlbumResponseModel> getAllAlbumsByUserId(@PathVariable String userId);


}

@Component
class AlbumsFallBackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable cause) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>cause: " + cause.toString());
        return null;
    }
}
