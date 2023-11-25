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
        Specification<Place> spec = placeSpecification.getPlaceSpecification(placeQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(placeQueryParam);
        Page<Place> response = placeRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }
    @Override
    public APIResponse create(Place place, MultipartFile image) {
        if (image != null) {
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "place");
            place.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            place.setImage(cloudinaryResponse.getUrl());
        }
        place = placeRepository.save(place);
        return new SuccessAPIResponse(place);
    }

    @Override
    public APIResponse update(Place place, MultipartFile image) {
        if(place == null){
            return new FailureAPIResponse("Place id is required!");
        }
        Place exists = placeRepository.findById(place.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find place with id: "+place.getId());
        }
        if (image != null) {
            cloudinaryService.deleteFile(place.getCloudinaryId());
            CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "place");
            place.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
            place.setImage(cloudinaryResponse.getUrl());
        }
        place = placeRepository.save(place);
        return new SuccessAPIResponse(place);
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
