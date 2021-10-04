package com.app.photo.api.user.data;

import com.app.photo.api.user.model.AlbumResponseModel;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="albums-ws", fallbackFactory= AlbumsFallBackFactory.class)
public interface AlbumsServiceClient {

    @GetMapping("/users/{userId}/albums")
    public List<AlbumResponseModel> getAllAlbumsByUserId(@PathVariable String userId);


}

@Component
class AlbumsFallBackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable cause) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>cause: " + cause.toString());
        return new AlbumsServiceClientFallback(cause);
    }
}

class AlbumsServiceClientFallback implements AlbumsServiceClient{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable cause;

    public AlbumsServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumResponseModel> getAllAlbumsByUserId(String userId) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>getAllAlbumsByUserId: " + userId);

        if(cause instanceof FeignException && ((FeignException) cause).status()== 404) {
            logger.error("404 error tool place when getAlbums was called with userId: " + userId);
            logger.error("Error Message: " + cause.getLocalizedMessage());
        } else {
            logger.error("Other Error Message: " + cause.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}