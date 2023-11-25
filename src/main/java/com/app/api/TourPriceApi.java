package com.app.api;

import com.app.entity.TourGuide;
import com.app.entity.TourPrice;
import com.app.payload.request.TourPriceQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.TourPriceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TourPriceApi {
    @Autowired
    TourPriceServices tourPriceServices;

    @GetMapping("/public/tour-prices")
    public ResponseEntity<?> getAllPrice(TourPriceQueryParam tourPriceQueryParam) {
        return ResponseEntity.ok(tourPriceServices.filterTourPrice(tourPriceQueryParam));
    }

    @PostMapping("/company/tour-prices")
    public ResponseEntity<?> createPrice(@RequestPart(name = "tourPrice") TourPrice tourPrice) {
        APIResponse response = tourPriceServices.create(tourPrice);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/tour-prices")
    public ResponseEntity<?> updatePrice(@RequestPart(name = "tourPrice") TourPrice tourPrice) {
        APIResponse response = tourPriceServices.update(tourPrice);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/tour-prices")
    public ResponseEntity<?> deletePrice(@RequestParam("id") Integer id) {
        APIResponse response = tourPriceServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/company/tour-prices/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = tourPriceServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/company/tour-prices/batch")
    public ResponseEntity<?> createTourPricesBatch(@RequestBody List<TourPrice> tourPrices) {
        APIResponse response = tourPriceServices.createBatch(tourPrices);
        return ResponseEntity.ok().body(response);
    }

}
