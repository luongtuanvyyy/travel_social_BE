package com.app.service;

import com.app.entity.Hotel;
import com.app.entity.Place;
import com.app.payload.request.HotelQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface HotelServices {
    APIResponse filterHotel(HotelQueryParam hotelQueryParam);
    APIResponse create(Hotel hotel, MultipartFile image);
    APIResponse update(Hotel hotel, MultipartFile image);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
}
