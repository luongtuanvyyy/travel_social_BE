package com.app.service;

import com.app.entity.Follow;
import com.app.entity.Like;
import com.app.payload.request.LikeQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface LikeServices {
    APIResponse filterLike(LikeQueryParam likeQueryParam);

    APIResponse create(Integer userId, UserPrincipal userPrincipal);

    APIResponse delete(Integer userId, UserPrincipal userPrincipal);

    APIResponse update(Like like);

    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Like> likes);

    APIResponse getListYouLike(UserPrincipal userPrincipal);
    APIResponse getListLikeYou(UserPrincipal userPrincipal);
}
