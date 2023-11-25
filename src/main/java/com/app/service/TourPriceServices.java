package com.app.service;

import com.app.entity.Follow;
import com.app.entity.TourPrice;
import com.app.payload.request.TourPriceQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TourPriceServices {
    APIResponse filterTourPrice(TourPriceQueryParam tourPriceQueryParam);
    APIResponse create(TourPrice tourPrice);
    APIResponse update(TourPrice tourPrice);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<TourPrice> tourPrices);
}
