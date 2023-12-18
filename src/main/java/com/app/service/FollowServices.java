package com.app.service;

import com.app.entity.Follow;
import com.app.entity.Voucher;
import com.app.payload.request.FollowQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FollowServices {
    APIResponse filterFollow(FollowQueryParam followQueryParam);
    APIResponse update(Follow follow);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Follow> follows);
    APIResponse create(Integer userId, UserPrincipal userPrincipal);
    APIResponse delete(Integer userId, UserPrincipal userPrincipal);
    APIResponse getListYouFollow(UserPrincipal userPrincipal);
    APIResponse getListFollowYou(UserPrincipal userPrincipal);
    APIResponse getTopFollower();
}
