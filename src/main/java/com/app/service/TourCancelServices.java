package com.app.service;

import com.app.entity.Follow;
import com.app.entity.TourCancel;
import com.app.payload.request.TourCancelQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourCancelServices {

    APIResponse filterTourCancel (TourCancelQueryParam tourCancelQueryParam);
    APIResponse create(TourCancel tourCancel);
    APIResponse update(TourCancel tourCancel);
    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<TourCancel> tourCancels);
}
