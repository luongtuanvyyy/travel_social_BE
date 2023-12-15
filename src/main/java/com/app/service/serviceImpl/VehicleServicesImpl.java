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
        try {
        Specification<Vehicle> spec = vehicleSpecification.getVehicleSpecification(vehicleQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(vehicleQueryParam);
        Page<Vehicle> response = vehicleRepository.findAll(spec, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse create(Vehicle vehicle) {
        try {
            if (vehicle.getName() == null || vehicle.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle name cannot be empty");
            }
            if (vehicle.getAddress() == null || vehicle.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle address cannot be empty");
            }
            if (vehicle.getHotline() == null || vehicle.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle hotline cannot be empty");
            }
            if (vehicle.getHotline() != null && !vehicle.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (vehicle.getType() == null || vehicle.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle type cannot be empty");
            }
            if (vehicle.getEmail() == null || vehicle.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle email cannot be empty");
            }


            vehicle = vehicleRepository.save(vehicle);
            return new SuccessAPIResponse(vehicle);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Vehicle vehicle) {
        try {
            if (vehicle == null) {
                return new FailureAPIResponse("Vehicle id is required!");
            }
            if (vehicle.getName() == null || vehicle.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle name cannot be empty");
            }
            if (vehicle.getAddress() == null || vehicle.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle address cannot be empty");
            }
            if (vehicle.getHotline() == null || vehicle.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle hotline cannot be empty");
            }
            if (vehicle.getHotline() != null && !vehicle.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (vehicle.getType() == null || vehicle.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle type cannot be empty");
            }
            if (vehicle.getEmail() == null || vehicle.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Vehicle email cannot be empty");
            }

            Vehicle exists = vehicleRepository.findById(vehicle.getId()).orElse(null);
            if (exists == null) {
                return new FailureAPIResponse("Cannot find vehicle with id: " + vehicle.getId());
            }

            vehicle = vehicleRepository.save(vehicle);
            return new SuccessAPIResponse(vehicle);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
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
