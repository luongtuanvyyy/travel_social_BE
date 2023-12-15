package com.app.service.serviceImpl;

import com.app.dto.AccountData;
import com.app.entity.Follow;
import com.app.payload.request.FollowQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.AccountRepository;
import com.app.repository.FollowRepository;
import com.app.service.FollowServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.FollowSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowServicesImpl implements FollowServices {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    FollowSpecification followSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterFollow(FollowQueryParam followQueryParam) {
        try {
        Specification<Follow> spec = followSpecification.getFollowSpecitification(followQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(followQueryParam);
        Page<Follow> response = followRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    public APIResponse getTopFollower(String email, FollowQueryParam followQueryParam) {
        try {
            Pageable pageable = requestParamsUtils.getPageableNoSort(followQueryParam);
            Page<AccountData> response = followRepository.findTopFollower( email , pageable);
            return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    public APIResponse getTopFollower(String email, FollowQueryParam followQueryParam) {
        try {
            Pageable pageable = requestParamsUtils.getPageableNoSort(followQueryParam);
            Page<AccountData> response = followRepository.findTopFollower( email , pageable);
            return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }


    @Override
    public APIResponse getFollowsByFollowerId(Integer followerId, FollowQueryParam followQueryParam) throws JsonProcessingException {
//        Specification<Follow> spec = followSpecification.getFollowSpecitification(followQueryParam);
        try {
        Pageable pageable = requestParamsUtils.getPageable(followQueryParam);
        Page<AccountData> response = accountRepository.findByFollowerId(followerId, pageable);
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
    public APIResponse getFollowsByGmail(String Gmail, FollowQueryParam followQueryParam) throws JsonProcessingException {
        try {
        Pageable pageable = requestParamsUtils.getPageable(followQueryParam);
        Page<AccountData> response = accountRepository.findFollowByGmail(Gmail, pageable);
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
    public APIResponse create(Follow follow) {
        try {
            follow = followRepository.save(follow);
            return new SuccessAPIResponse(follow);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Follow follow) {
        if (follow == null) {
            return new FailureAPIResponse("Follow id is required!");
        }
        Follow exists = followRepository.findById(follow.getId()).orElse(null);
        if (exists == null) {
            return new FailureAPIResponse("Cannot find follow with id: " + follow.getId());
        }

        follow = followRepository.save(follow);
        return new SuccessAPIResponse(follow);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            followRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Follow.class, followRepository);
    }

    @Override
    public APIResponse createBatch(List<Follow> follows) {
        List<Follow> createdFollows = new ArrayList<>();
        for (Follow follow : follows) {
            Follow createdFollow = followRepository.save(follow);
            createdFollows.add(createdFollow);
        }
        return new SuccessAPIResponse(createdFollows);
    }
}
