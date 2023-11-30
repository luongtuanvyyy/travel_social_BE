package com.app.service;

import com.app.entity.Follow;
import com.app.entity.Voucher;
import com.app.payload.request.FollowQueryParam;
import com.app.payload.response.APIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FollowServices {
    APIResponse filterFollow(FollowQueryParam followQueryParam);


    APIResponse getFollowsByFollowerId(Integer followerId, FollowQueryParam followQueryParam) throws JsonProcessingException;

    APIResponse getFollowsByGmail(String Gmail, FollowQueryParam followQueryParam) throws JsonProcessingException;

    APIResponse create(Follow follow);
    APIResponse update(Follow follow);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Follow> follows);
}
