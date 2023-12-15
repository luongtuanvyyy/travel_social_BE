package com.app.service.serviceImpl;

import com.app.entity.TourTemplate;
import com.app.entity.Vehicle;
import com.app.entity.View;
import com.app.payload.request.ViewQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.ViewRepository;
import com.app.service.ViewServices;
import com.app.speficication.ViewSpecification;
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
public class ViewServicesImpl implements ViewServices {
    @Autowired
    ViewRepository viewRepository;
    @Autowired
    ViewSpecification viewSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterView(ViewQueryParam viewQueryParam) {
        try {
        Specification<View> spec = viewSpecification.getViewSpecification(viewQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(viewQueryParam);
        Page<View> response = viewRepository.findAll(spec, pageable);
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
    public APIResponse create(View view) {
        try {
        view = viewRepository.save(view);
        return new SuccessAPIResponse(view);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(View view) {
        if(view == null){
            return  new FailureAPIResponse("View id is required!");
        }
        View exists = viewRepository.findById(view.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find View with id: "+view.getId());
        }
        view = viewRepository.save(view);
        return new SuccessAPIResponse(view);
    }


    @Override
    public APIResponse delete(Integer id) {
        try {
            viewRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, View.class, viewRepository);
    }

    @Override
    public APIResponse createBatch(List<View> views) {
        List<View> createdViews = new ArrayList<>();
        for (View view : views) {
            View createdView = viewRepository.save(view);
            createdViews.add(createdView);
        }
        return new SuccessAPIResponse(createdViews);
    }
}
