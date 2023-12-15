package com.app.service.serviceImpl;

import com.app.entity.Place;
import com.app.entity.TourDetail;
import com.app.entity.TourGuide;
import com.app.payload.request.TourGuideQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.CloudinaryResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.TuorGuideRepository;
import com.app.service.TourGuideServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.TourGuideSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class TourGuideServicesImpl implements TourGuideServices {
    @Autowired
    TuorGuideRepository tuorGuideRepository;
    @Autowired
    TourGuideSpecification tourGuideSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterTourGuide(TourGuideQueryParam tourGuideQueryParam) {
        try {
        Specification<TourGuide> spec = tourGuideSpecification.getTourGuideSpecification(tourGuideQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(tourGuideQueryParam);
        Page<TourGuide> response = tuorGuideRepository.findAll(spec, pageable);
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
    public APIResponse create(TourGuide tourGuide, MultipartFile image) {
        try {
            if (tourGuide.getFullName() == null || tourGuide.getFullName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tour guide name cannot be empty");
            }
            if (image != null) {
                CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "TourGuide");
                tourGuide.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
                tourGuide.setAvatar(cloudinaryResponse.getUrl());
            }
            tourGuide = tuorGuideRepository.save(tourGuide);
            return new SuccessAPIResponse(tourGuide);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(TourGuide tourGuide, MultipartFile image) {
        try {

            if (tourGuide == null) {
                return new FailureAPIResponse("Tour guide id is required!");
            }
            if (tourGuide.getFullName() == null || tourGuide.getFullName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tour guide name cannot be empty");
            }
            TourGuide exists = tuorGuideRepository.findById(tourGuide.getId()).orElse(null);
            if (exists == null) {
                return new FailureAPIResponse("Cannot find tour guide with id: " + tourGuide.getId());
            }
            if (image != null) {
                cloudinaryService.deleteFile(tourGuide.getCloudinaryId());
                CloudinaryResponse cloudinaryResponse = cloudinaryService.uploadFile(image, "TourGuide");
                tourGuide.setCloudinaryId(cloudinaryResponse.getCloudinaryId());
                tourGuide.setAvatar(cloudinaryResponse.getUrl());
            }
            tourGuide = tuorGuideRepository.save(tourGuide);
            return new SuccessAPIResponse(tourGuide);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            tuorGuideRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, TourGuide.class, tuorGuideRepository);
    }

    @Override
    public APIResponse createBatch(List<TourGuide> tourGuides) {
        List<TourGuide> createdTourGuides = new ArrayList<>();
        for (TourGuide tourGuide : tourGuides) {
            TourGuide createdTourGuide = tuorGuideRepository.save(tourGuide);
            createdTourGuides.add(createdTourGuide);
        }
        return new SuccessAPIResponse(createdTourGuides);
    }
}
