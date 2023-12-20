package com.app.api;

import com.app.entity.TourCancel;
import com.app.entity.TourDetail;
import com.app.payload.request.TourDetailQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.TourDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TourDetailApi {
    @Autowired
    TourDetailServices tourDetailServices;

    @GetMapping("/public/tour-details")
    public ResponseEntity<?> filter(TourDetailQueryParam tourDetailQueryParam) {
        return ResponseEntity.ok(tourDetailServices.filterTourDetail(tourDetailQueryParam));
    }

    @PostMapping("/company/tour-details")
    public ResponseEntity<?> createTourDetail(@RequestBody TourDetail tourDetail) {
        APIResponse response = tourDetailServices.create(tourDetail);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/tour-details")
    public ResponseEntity<?> updateTourDetail(@RequestPart(name = "tour") TourDetail tour) {
        APIResponse response = tourDetailServices.update(tour);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/company/tour-details")
    public ResponseEntity<?> deleteTourDetail(@RequestParam("id") Integer id) {
        APIResponse response = tourDetailServices.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/company/tour-details/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = tourDetailServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/company/tour-details/batch")
    public ResponseEntity<?> createTourDetailsBatch(@RequestBody List<TourDetail> tourDetails) {
        APIResponse response = tourDetailServices.createBatch(tourDetails);
        return ResponseEntity.ok().body(response);
    }

}