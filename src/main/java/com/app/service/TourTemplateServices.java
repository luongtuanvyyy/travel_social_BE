package com.app.service;

import com.app.entity.Follow;
import com.app.entity.TourTemplate;
import com.app.payload.request.TourTemplateQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourTemplateServices {
    APIResponse filterTourTemplate(TourTemplateQueryParam tourTemplateQueryParam);

    APIResponse create(TourTemplate tourTemplate);
    APIResponse update(TourTemplate tourTemplate);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<TourTemplate> tourTemplates);

}
