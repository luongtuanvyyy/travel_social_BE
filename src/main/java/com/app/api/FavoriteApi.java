package com.app.api;

import com.app.entity.BookingNotification;
import com.app.entity.Favorite;
import com.app.payload.request.FavoriteQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.FavoriteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FavoriteApi {
    @Autowired
    FavoriteServices favoriteServices;

    @GetMapping("/user/favorites")
    public ResponseEntity<?> getAllFavorite(FavoriteQueryParam favoriteQueryParam) {
        return ResponseEntity.ok(favoriteServices.filterFavorite(favoriteQueryParam));
    }

    @PostMapping("/user/favorites")
    public ResponseEntity<?> createFavorite(@RequestPart(name = "favorite") Favorite favorite) {
        APIResponse response = favoriteServices.create(favorite);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/user/favorites")
    public ResponseEntity<?> deleteFavorite(@RequestParam("id") Integer id) {
        APIResponse response = favoriteServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/favorites/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = favoriteServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/user/favorites/batch")
    public ResponseEntity<?> createFavoritesBatch(@RequestBody List<Favorite> favorites) {
        APIResponse response = favoriteServices.createBatch(favorites);
        return ResponseEntity.ok().body(response);
    }
}
