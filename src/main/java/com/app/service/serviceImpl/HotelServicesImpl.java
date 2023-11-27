package com.app.service.serviceImpl;

import com.app.entity.Hotel;
import com.app.payload.request.HotelQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.CloudinaryResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.HotelRepository;
import com.app.service.HotelServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.HotelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HotelServicesImpl implements HotelServices {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelSpecification hotelSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterHotel(HotelQueryParam hotelQueryParam) {
        Specification<Hotel> spec = hotelSpecification.getHotelSpecification(hotelQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(hotelQueryParam);
        Page<Hotel> response = hotelRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Hotel hotel, MultipartFile image) {
        try {
            if (hotel.getName() == null || hotel.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel name cannot be empty");
            }
            if (hotel.getAddress() == null || hotel.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel address cannot be empty");
            }
            if (hotel.getHotline() == null || hotel.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel hotline cannot be empty");
            }
            if (hotel.getHotline() != null && !hotel.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (hotel.getType() == null || hotel.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel type cannot be empty");
            }
            if (hotel.getRoom() <= 0) {
                throw new IllegalArgumentException("Hotel room cannot be empty");
            }

            if (image != null) {
                CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "hotel");
                hotel.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
                hotel.setImage(cloudinaryResponse.getUrl());
            }

            hotel = hotelRepository.save(hotel);
            return new SuccessAPIResponse(hotel);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Hotel hotel, MultipartFile image) {
        try {
            if (hotel == null) {
                return new FailureAPIResponse("Hotel id is required!");
            }
            if (hotel.getName() == null || hotel.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel name cannot be empty");
            }
            if (hotel.getAddress() == null || hotel.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel address cannot be empty");
            }
            if (hotel.getHotline() == null || hotel.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel hotline cannot be empty");
            }
            if (hotel.getHotline() != null && !hotel.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (hotel.getType() == null || hotel.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Hotel type cannot be empty");
            }
            if (hotel.getRoom() <= 0) {
                throw new IllegalArgumentException("Hotel room cannot be empty");
            }

            Hotel exists = hotelRepository.findById(hotel.getId()).orElse(null);
            if (exists == null) {
                return new FailureAPIResponse("Cannot find hotel with id: " + hotel.getId());
            }
            if (image != null) {
                cloudinaryService.deleteFile(hotel.getCloudinaryId());
                CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "hotel");
                hotel.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
                hotel.setImage(cloudinaryResponse.getUrl());
            }
            hotel = hotelRepository.save(hotel);
            return new SuccessAPIResponse(hotel);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            hotelRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Hotel.class, hotelRepository);
    }
}
