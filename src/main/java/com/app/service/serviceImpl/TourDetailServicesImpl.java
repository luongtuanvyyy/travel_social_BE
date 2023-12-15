package com.app.service.serviceImpl;

import com.app.entity.Review;
import com.app.entity.TourCancel;
import com.app.entity.TourDetail;
import com.app.payload.request.TourDetailQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.TourDetailRepository;
import com.app.service.TourDetailServices;
import com.app.speficication.TourDetailSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourDetailServicesImpl implements TourDetailServices {

    @Autowired
    TourDetailRepository tourDetailRepository;

    @Autowired
    TourDetailSpecification tourDetailSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterTourDetail(TourDetailQueryParam tourDetailQueryParam) {
        try {
        Specification<TourDetail> spec = tourDetailSpecification.getTourDetailSpecification(tourDetailQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(tourDetailQueryParam);
        Page<TourDetail> response = tourDetailRepository.findAll(spec, pageable);
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
    public APIResponse create(TourDetail tourDetail) {
        try {
        tourDetail = tourDetailRepository.save(tourDetail);
        return new SuccessAPIResponse(tourDetail);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(TourDetail tourDetail) {
        try {
        if(tourDetail == null){
            return  new FailureAPIResponse("Tour id is required!");
        }
        TourDetail exists = tourDetailRepository.findById(tourDetail.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find tour with id: "+tourDetail.getId());
        }
        tourDetail = tourDetailRepository.save(tourDetail);
        return new SuccessAPIResponse(tourDetail);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            tourDetailRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, TourDetail.class, tourDetailRepository);
    }

    @Override
    public APIResponse createBatch(List<TourDetail> tourDetails) {
        List<TourDetail> createdTourDetails = new ArrayList<>();
        for (TourDetail tourDetail : tourDetails) {
            TourDetail createdTourDetail = tourDetailRepository.save(tourDetail);
            createdTourDetails.add(createdTourDetail);
        }
        return new SuccessAPIResponse(createdTourDetails);
    }
}