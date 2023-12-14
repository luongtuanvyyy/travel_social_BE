package com.app.service.serviceImpl;

import com.app.entity.Tour;
import com.app.entity.TourTemplate;
import com.app.entity.Vehicle;
import com.app.payload.request.TourTemplateQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.TourTemplateRepository;
import com.app.service.TourTemplateServices;
import com.app.speficication.TourTemplateSpecification;
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
public class TourTemplateServicesImpl implements TourTemplateServices {
    @Autowired
    TourTemplateRepository tourTemplateRepository;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    TourTemplateSpecification tourTemplateSpecification;

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterTourTemplate(TourTemplateQueryParam tourTemplateQueryParam) {
        try {
        Specification<TourTemplate> spec = tourTemplateSpecification.getTourTemplateSpecification(tourTemplateQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(tourTemplateQueryParam);
        Page<TourTemplate> response = tourTemplateRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse create(TourTemplate tourTemplate) {
        try {
        tourTemplate = tourTemplateRepository.save(tourTemplate);
        return new SuccessAPIResponse(tourTemplate);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(TourTemplate tourTemplate) {
        try {
        if(tourTemplate == null){
            return  new FailureAPIResponse("Tour template id is required!");
        }
        TourTemplate exists = tourTemplateRepository.findById(tourTemplate.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find tour template with id: "+tourTemplate.getId());
        }
        tourTemplate = tourTemplateRepository.save(tourTemplate);
        return new SuccessAPIResponse(tourTemplate);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            tourTemplateRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, TourTemplate.class, tourTemplateRepository);
    }

    @Override
    public APIResponse createBatch(List<TourTemplate> tourTemplates) {
        List<TourTemplate> createdTourTemplates = new ArrayList<>();
        for (TourTemplate tourTemplate : tourTemplates) {
            TourTemplate createdTourTemplate = tourTemplateRepository.save(tourTemplate);
            createdTourTemplates.add(createdTourTemplate);
        }
        return new SuccessAPIResponse(createdTourTemplates);
    }

}
