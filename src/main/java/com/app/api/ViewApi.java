package com.app.api;

import com.app.entity.TourTemplate;
import com.app.entity.View;
import com.app.payload.request.ViewQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.ViewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ViewApi {
    @Autowired
    ViewServices viewServices;

    @GetMapping("/public/views")
    public ResponseEntity<?> filterView(ViewQueryParam viewQueryParam) {
        return ResponseEntity.ok(viewServices.filterView(viewQueryParam));
    }

    @PostMapping("/user/views")
    public ResponseEntity<?> createView(@RequestPart(name = "view") View view) {
        APIResponse response = viewServices.create(view);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/views")
    public ResponseEntity<?> deleteView(@RequestParam("id") Integer id) {
        APIResponse response = viewServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/user/views/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = viewServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/admin/user/views/batch")
    public ResponseEntity<?> createVouchersBatch(@RequestBody List<View> views) {
        APIResponse response = viewServices.createBatch(views);
        return ResponseEntity.ok().body(response);
    }
}
