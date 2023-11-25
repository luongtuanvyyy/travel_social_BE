package com.app.api;

import com.app.entity.TourDetail;
import com.app.entity.TourGuide;
import com.app.payload.request.TourGuideQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.TourGuideServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TourGuideApi {
    @Autowired
    TourGuideServices tourGuideServices;

    @GetMapping("/public/tour-guides")
    public ResponseEntity<?> getAllTourGuide(TourGuideQueryParam hotelQueryParam) {
        return ResponseEntity.ok(tourGuideServices.filterTourGuide(hotelQueryParam));
    }

    @PostMapping("/company/tour-guides")
    public ResponseEntity<?> createTourGuide(@RequestPart(name = "tourGuide") TourGuide tourGuide,
                                             @RequestPart(name = "image") @Nullable MultipartFile image) {
        APIResponse response = tourGuideServices.create(tourGuide, image);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/tour-guides")
    public ResponseEntity<?> updateTourGuide(@RequestPart(name = "tourGuide") TourGuide tourGuide,
                                             @RequestPart(name = "image") @Nullable MultipartFile image) {
        APIResponse response = tourGuideServices.update(tourGuide, image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/tour-guides")
    public ResponseEntity<?> deleteTourGuide(@RequestParam("id") Integer id) {
        APIResponse response = tourGuideServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/tour-guides/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = tourGuideServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/admin/tour-guides/batch")
    public ResponseEntity<?> createTourGuidesBatch(@RequestBody List<TourGuide> tourGuides) {
        APIResponse response = tourGuideServices.createBatch(tourGuides);
        return ResponseEntity.ok().body(response);
    }
}
