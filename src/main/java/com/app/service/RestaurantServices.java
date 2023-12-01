package com.app.service;

import com.app.entity.Restaurant;
import com.app.payload.request.RestaurantQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface RestaurantServices {
    APIResponse filterRestaurant(RestaurantQueryParam restaurantQueryParam);
    APIResponse create(Restaurant restaurant);
    APIResponse update(Restaurant restaurant);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
}
