package com.app.api;

import com.app.entity.Tour;
import com.app.entity.TourCancel;
import com.app.payload.request.TourCancelQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.TourCancelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TourCancelApi {
    @Autowired
    TourCancelServices tourCancelServices;
// company
    @GetMapping("/company/tour-cancels")
    public ResponseEntity<?> filterTourCancel(TourCancelQueryParam tourCancelQueryParam) {
        return ResponseEntity.ok(tourCancelServices.filterTourCancel(tourCancelQueryParam));
    }

    @PostMapping("/company/tour-cancels")
    public ResponseEntity<?> createTourCancel(@RequestPart(name = "tour_cancel") TourCancel tour_cancel) {
        APIResponse response = tourCancelServices.create(tour_cancel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/company/tour-cancels")
    public ResponseEntity<?> updateTourCancel(@RequestPart(name = "tour_cancel") TourCancel tour_cancel) {
        APIResponse response = tourCancelServices.update(tour_cancel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/tour-cancels")
    public ResponseEntity<?> deleteTourCancel(@RequestParam("id") Integer id) {
        APIResponse response = tourCancelServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/company/tour-cancels/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = tourCancelServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/company/tour-cancels/batch")
    public ResponseEntity<?> createTourCancelsBatch(@RequestBody List<TourCancel> tourCancels) {
        APIResponse response = tourCancelServices.createBatch(tourCancels);
        return ResponseEntity.ok().body(response);
    }
}
