package com.app.api;

import com.app.entity.Restaurant;
import com.app.payload.request.RestaurantQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.RestaurantServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class RestaurantApi {
    @Autowired
    RestaurantServices restaurantServices;

    @GetMapping("/public/restaurants")
    public ResponseEntity<?> filter(RestaurantQueryParam restaurantQueryParam) {
        return ResponseEntity.ok(restaurantServices.filterRestaurant(restaurantQueryParam));
    }

    @PostMapping("/company/restaurants")
    public ResponseEntity<?> createRestaurants(@RequestPart(name = "restaurant") Restaurant restaurant,
                                               @RequestPart(name = "image") MultipartFile image) {
        APIResponse response = restaurantServices.create(restaurant, image);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/restaurants")
    public ResponseEntity<?> updateRestaurants(@RequestPart(name = "restaurant") Restaurant restaurant,
                                               @Nullable @RequestPart(name = "image") MultipartFile image) {
        APIResponse response = restaurantServices.update(restaurant, image);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/company/restaurants")
    public ResponseEntity<?> deleteRestaurants(@RequestParam("id") Integer id) {
        APIResponse response = restaurantServices.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/company/restaurants/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = restaurantServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
}
