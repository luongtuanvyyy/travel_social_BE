package com.app.service;

import com.app.entity.Follow;
import com.app.entity.View;
import com.app.payload.request.ViewQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ViewServices {
    APIResponse filterView(ViewQueryParam viewQueryParam);


    APIResponse create(View view);

    APIResponse update(View view);

    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);

    APIResponse createBatch(List<View> views);
}
