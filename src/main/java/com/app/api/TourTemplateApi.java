package com.app.api;

import com.app.entity.TourTemplate;
import com.app.payload.request.TourTemplateQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.TourTemplateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TourTemplateApi {
    //tu tu tinh
    @Autowired
    TourTemplateServices tourTemplateServices;
    @GetMapping("/company/tour-templates")
    public ResponseEntity<?> filter(TourTemplateQueryParam tourTemplateQueryParam) {
        return ResponseEntity.ok(tourTemplateServices.filterTourTemplate(tourTemplateQueryParam));
    }
    @PostMapping("/company/tour-templates")
    public ResponseEntity<?> createTour(@RequestPart(name = "tourTemplate") TourTemplate tourTemplate){
        APIResponse response = tourTemplateServices.create(tourTemplate);
        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/company/tour-templates")
    public ResponseEntity<?> updateTour(@RequestPart(name = "tourTemplate") TourTemplate tourTemplate){
        APIResponse response = tourTemplateServices.update(tourTemplate);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/company/tour-templates")
    public ResponseEntity<?> deleteTour(@RequestParam("id") Integer id){
        APIResponse response = tourTemplateServices.delete(id);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/company/tour-templates/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = tourTemplateServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/company/tour-templates/batch")
    public ResponseEntity<?> createTourTemplatesBatch(@RequestBody List<TourTemplate> tourTemplates) {
        APIResponse response = tourTemplateServices.createBatch(tourTemplates);
        return ResponseEntity.ok().body(response);
    }
}
