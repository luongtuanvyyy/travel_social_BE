package com.app.service.serviceImpl;


import com.app.entity.Place;
import com.app.payload.request.PlaceQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.CloudinaryResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.PlaceRepository;
import com.app.service.PlaceServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.PlaceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlaceServicesImpl implements PlaceServices {
    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    PlaceSpecification placeSpecification;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;

    public APIResponse filterPlace(PlaceQueryParam placeQueryParam) {
        try {
        Specification<Place> spec = placeSpecification.getPlaceSpecification(placeQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(placeQueryParam);
        Page<Place> response = placeRepository.findAll(spec, pageable);
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
    public APIResponse create(Place place) {
        try {
            if (place.getName() == null || place.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Place name cannot be empty");
            }
            if (place.getAddress() == null || place.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Place address cannot be empty");
            }
            if (place.getHotline() == null || place.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Place hotnamw cannot be empty");
            }
            if (place.getHotline() != null && !place.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (place.getType() == null || place.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Place type cannot be empty");
            }

            place = placeRepository.save(place);
            return new SuccessAPIResponse(place);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Place place) {
        try {
            if (place == null) {
                return new FailureAPIResponse("Place id is required!");
            }
            if (place.getName() == null || place.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Place name cannot be empty");
            }
            if (place.getAddress() == null || place.getAddress().trim().isEmpty()) {
                throw new IllegalArgumentException("Place address cannot be empty");
            }
            if (place.getHotline() == null || place.getHotline().trim().isEmpty()) {
                throw new IllegalArgumentException("Place hotnamw cannot be empty");
            }
            if (place.getHotline() != null && !place.getHotline().matches("\\d{10,11}")) {
                throw new IllegalArgumentException("Invalid phone number");
            }
            if (place.getType() == null || place.getType().trim().isEmpty()) {
                throw new IllegalArgumentException("Place type cannot be empty");
            }

            Place exists = placeRepository.findById(place.getId()).orElse(null);
            if (exists == null) {
                return new FailureAPIResponse("Cannot find place with id: " + place.getId());
            }
            place = placeRepository.save(place);
            return new SuccessAPIResponse(place);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            placeRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Place.class, placeRepository);
    }

}
