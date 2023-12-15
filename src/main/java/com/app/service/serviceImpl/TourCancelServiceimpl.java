package com.app.service.serviceImpl;

import com.app.entity.Review;
import com.app.entity.TourCancel;
import com.app.entity.TourDetail;
import com.app.payload.request.TourCancelQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.TourCancelRepository;
import com.app.service.TourCancelServices;
import com.app.speficication.TourCancelSpecification;
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
public class TourCancelServiceimpl implements TourCancelServices {
    @Autowired
    TourCancelSpecification tourCancelSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    TourCancelRepository tourCancelRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterTourCancel(TourCancelQueryParam tourCancelQueryParam) {
        try {
        Specification<TourCancel> spec = tourCancelSpecification.getTourCanceSpecitification(tourCancelQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(tourCancelQueryParam);
        Page<TourCancel> response = tourCancelRepository.findAll(spec, pageable);
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
    public APIResponse create(TourCancel tourCancel) {
        try {
        tourCancel = tourCancelRepository.save(tourCancel);
        return new SuccessAPIResponse(tourCancel);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(TourCancel tourCancel) {
        try {
        if(tourCancel == null){
            return  new FailureAPIResponse("Tour cancel id is required!");
        }
        TourCancel exists = tourCancelRepository.findById(tourCancel.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find tour cancel with id: "+tourCancel.getId());
        }
        tourCancel = tourCancelRepository.save(tourCancel);
        return new SuccessAPIResponse(tourCancel);
    } catch (Exception ex) {
        return new FailureAPIResponse(ex.getMessage());
    }
    }


    @Override
    public APIResponse delete(Integer id) {
        try {
            tourCancelRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, TourCancel.class, tourCancelRepository);
    }

    @Override
    public APIResponse createBatch(List<TourCancel> tourCancels) {
        List<TourCancel> createdTourCancels = new ArrayList<>();
        for (TourCancel tourCancel : tourCancels) {
            TourCancel createdTourCancel = tourCancelRepository.save(tourCancel);
            createdTourCancels.add(createdTourCancel);
        }
        return new SuccessAPIResponse(createdTourCancels);
    }
}
