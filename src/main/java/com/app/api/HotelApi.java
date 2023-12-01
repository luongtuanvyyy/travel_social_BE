package com.app.api;

import com.app.entity.Hotel;
import com.app.payload.request.HotelQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class HotelApi {
    @Autowired
    HotelServices hotelServices;

    @GetMapping("/public/hotels")
    public ResponseEntity<?> getAllHotel(@Valid HotelQueryParam hotelQueryParam) throws Exception {
        return ResponseEntity.ok(hotelServices.filterHotel(hotelQueryParam));
    }

    @PostMapping("/company/hotels")
    public ResponseEntity<?> createHotel(@RequestPart(name = "hotel") Hotel hotel) {
        APIResponse response = hotelServices.create(hotel);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/hotels")
    public ResponseEntity<?> updateHotel(@RequestPart(name = "hotel") Hotel hotel) {
        APIResponse response = hotelServices.update(hotel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/hotels")
    public ResponseEntity<?> deleteHotel(@RequestParam("id") Integer id) {
        APIResponse response = hotelServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/company/hotels/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = hotelServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
}
