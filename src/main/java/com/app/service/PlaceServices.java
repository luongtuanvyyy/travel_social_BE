package com.app.service;


import com.app.entity.Place;
import com.app.payload.request.PlaceQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceServices {
    APIResponse filterPlace(PlaceQueryParam placeQueryParam);
    APIResponse create(Place place, MultipartFile image);
    APIResponse update(Place place, MultipartFile image);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
}
