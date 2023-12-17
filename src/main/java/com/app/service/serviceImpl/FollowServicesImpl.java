package com.app.service.serviceImpl;

import com.app.dto.AccountData;
import com.app.dto.Notification;
import com.app.entity.Account;
import com.app.entity.Follow;
import com.app.mapper.AccountMapper;
import com.app.payload.request.FollowQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.AccountRepository;
import com.app.repository.FollowRepository;
import com.app.security.UserPrincipal;
import com.app.service.FollowServices;
import com.app.speficication.FollowSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private AccountMapper accountMapper;

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
    @Override
    public APIResponse create(Integer userId, UserPrincipal userPrincipal) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isEmpty()) {
            return new FailureAPIResponse("This userId does not exist");
        }
        Account existAcc = optionalAccount.get();
        Follow existingFollow = followRepository.findByAccountAndCreatedBy(existAcc, userPrincipal.getEmail());
        if (existingFollow != null) {
            return new FailureAPIResponse("You have already followed this userId");
        }
        Follow follow = new Follow();
        follow.setAccount(existAcc);
        try {
            followRepository.save(follow);
            return new SuccessAPIResponse("Follow successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
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
    public APIResponse delete(Integer userId, UserPrincipal userPrincipal) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isEmpty()) {
            return new FailureAPIResponse("This userId does not exist");
        }
        Account existAcc = optionalAccount.get();
        Follow existingFollow = followRepository.findByAccountAndCreatedBy(existAcc, userPrincipal.getEmail());
        if (existingFollow == null) {
            return new FailureAPIResponse("You have already unfollowed this userId");
        }
        try {
            followRepository.delete(existingFollow);
            return new SuccessAPIResponse("UnFollow successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
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

    @Override
    public APIResponse getListYouFollow(UserPrincipal userPrincipal) {
        System.out.println("Email: " + userPrincipal.getEmail());
        List<Follow> listYouFollow = followRepository.findByCreatedBy(userPrincipal.getEmail());
        if (listYouFollow.size() == 0) {
            new SuccessAPIResponse("You haven't followed anyone yet");
        }

        List<Notification> peopleYouFollow = listYouFollow.stream()
                .map(accountMapper::notificationFromFollow)
                .collect(Collectors.toList());
        return new SuccessAPIResponse("Get the list of people you followed successfully", peopleYouFollow);
    }

    @Override
    public APIResponse getListFollowYou(UserPrincipal userPrincipal) {
        List<Follow> listFollowYou = followRepository.findByAccount(userPrincipal.toAccount());
        if (listFollowYou.size() == 0) {
            return new SuccessAPIResponse("You don't have any followers yet");
        }
        List<Notification> peopleFollowYou = listFollowYou.stream()
                .map(accountMapper::notificationFromFollow)
                .collect(Collectors.toList());
        return new SuccessAPIResponse("Get your follower list successfully", peopleFollowYou);
    }
    @Override
    public APIResponse getTopFollower() {
        try {
            List<AccountData> response = followRepository.getAccountsOrderByFollowerCount();
            return new APIResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new FailureAPIResponse(ex.getMessage());
        }
    }
}
