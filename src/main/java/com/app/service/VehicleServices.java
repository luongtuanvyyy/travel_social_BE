package com.app.service;

import com.app.entity.Vehicle;
import com.app.payload.request.VehicleQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VehicleServices {
    APIResponse filterVehicle(VehicleQueryParam vehicleQueryParam);
    APIResponse create(Vehicle vehicle);
    APIResponse update(Vehicle vehicle);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
}
