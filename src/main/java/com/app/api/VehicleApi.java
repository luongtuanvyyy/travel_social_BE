package com.app.api;

import com.app.entity.Vehicle;
import com.app.payload.request.VehicleQueryParam;
import com.app.payload.response.APIResponse;
import com.app.repository.VehicleRepository;
import com.app.service.VehicleServices;
import com.app.service.serviceImpl.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class VehicleApi {
    @Autowired
    VehicleServices vehicleServices;
    @Autowired
    ImportExcelService importExcelService;
    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/public/vehicles")
    public ResponseEntity<?> filter(VehicleQueryParam vehicleQueryParam) {
        return ResponseEntity.ok(vehicleServices.filterVehicle(vehicleQueryParam));
    }

    @PostMapping("/company/vehicles/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = vehicleServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/company/vehicles")
    public ResponseEntity<?> createVehicle(@RequestPart(name = "vehicle") Vehicle vehicle) {
        APIResponse response = vehicleServices.create(vehicle);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/vehicles")
    public ResponseEntity<?> updateVehicle(@RequestPart(name = "vehicle") Vehicle vehicle) {
        APIResponse response = vehicleServices.update(vehicle);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/vehicles")
    public ResponseEntity<?> deleteVehicle(@RequestParam("id") Integer id) {
        APIResponse response = vehicleServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
