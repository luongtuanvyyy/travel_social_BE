package com.app.service;

import com.app.entity.Follow;
import com.app.entity.Like;
import com.app.payload.request.LikeQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LikeServices {
    APIResponse filterLike(LikeQueryParam likeQueryParam);


    APIResponse create(Like like);

    APIResponse update(Like like);

    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Like> likes);
}
