package com.app.service.serviceImpl;

import com.app.entity.Vehicle;
import com.app.payload.request.VehicleQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.CloudinaryResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.VehicleRepository;
import com.app.service.VehicleServices;
import com.app.speficication.VehicleSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleServicesImpl implements VehicleServices {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    VehicleSpecification vehicleSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterVehicle(VehicleQueryParam vehicleQueryParam) {
        Specification<Vehicle> spec = vehicleSpecification.getVehicleSpecification(vehicleQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(vehicleQueryParam);
        Page<Vehicle> response = vehicleRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Vehicle vehicle, MultipartFile image) {
        if (image != null) {
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "vehicle");
            vehicle.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            vehicle.setImage(cloudinaryResponse.getUrl());
        }
        vehicle = vehicleRepository.save(vehicle);
        return new SuccessAPIResponse(vehicle);
    }

    @Override
    public APIResponse update(Vehicle vehicle, MultipartFile image) {
        if (vehicle == null) {
            return new FailureAPIResponse("Vehicle id is required!");
        }
        Vehicle exists = vehicleRepository.findById(vehicle.getId()).orElse(null);
        if (exists == null) {
            return new FailureAPIResponse("Cannot find vehicle with id: " + vehicle.getId());
        }
        if (image != null) {
            cloudinaryService.deleteFile(vehicle.getCloudinaryId());
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "vehicle");
            vehicle.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            vehicle.setImage(cloudinaryResponse.getUrl());
        }
        vehicle = vehicleRepository.save(vehicle);
        return new SuccessAPIResponse(vehicle);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            vehicleRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Vehicle.class, vehicleRepository);
    }
}
