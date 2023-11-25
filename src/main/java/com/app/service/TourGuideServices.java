package com.app.service;

import com.app.entity.Follow;
import com.app.entity.TourGuide;
import com.app.payload.request.TourGuideQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourGuideServices {
    APIResponse filterTourGuide(TourGuideQueryParam tourGuideQueryParam);
    APIResponse create(TourGuide tourGuide, MultipartFile image);
    APIResponse update(TourGuide tourGuide, MultipartFile image);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<TourGuide> tourGuides);
}
