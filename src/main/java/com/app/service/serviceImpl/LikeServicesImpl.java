package com.app.service.serviceImpl;

import com.app.dto.Notification;
import com.app.entity.Account;
import com.app.entity.Like;
import com.app.mapper.AccountMapper;
import com.app.payload.request.LikeQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.AccountRepository;
import com.app.repository.LikeRepository;
import com.app.security.UserPrincipal;
import com.app.service.LikeServices;
import com.app.speficication.LikeSpecification;
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
public class LikeServicesImpl implements LikeServices  {

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    LikeSpecification likeSpecification;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    ImportExcelService importExcelService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public APIResponse filterLike(LikeQueryParam likeQueryParam) {
        try {
        Specification<Like> spec = likeSpecification.getLikeSpecitification(likeQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(likeQueryParam);
        Page<Like> response = likeRepository.findAll(spec, pageable);
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
    public APIResponse create(Integer userId, UserPrincipal userPrincipal) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isEmpty()) {
            return new FailureAPIResponse("This userId does not exist");
        }
        Account existAcc = optionalAccount.get();
        Like existingLike = likeRepository.findByAccountAndCreatedBy(existAcc, userPrincipal.getEmail());
        if (existingLike != null) {
            return new FailureAPIResponse("You have already liked this userId");
        }
        Like like = new Like();
        like.setAccount(existAcc);
        try {
            likeRepository.save(like);
            return new SuccessAPIResponse("Like successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }


    @Override
    public APIResponse delete(Integer userId, UserPrincipal userPrincipal) {
        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isEmpty()) {
            return new FailureAPIResponse("This userId does not exist");
        }
        Account existAcc = optionalAccount.get();
        Like existingLike = likeRepository.findByAccountAndCreatedBy(existAcc, userPrincipal.getEmail());
        if (existingLike == null) {
            return new FailureAPIResponse("You have already unliked this userId");
        }
        try {
            likeRepository.deleteById(existingLike.getId());
            return new SuccessAPIResponse("Unlike successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse("Error: " + ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Like like) {
        try {
        if(like == null){
            return  new FailureAPIResponse("Like id is required!");
        }
        Like exists = likeRepository.findById(like.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find Like with id: "+like.getId());
        }
        like = likeRepository.save(like);
        return new SuccessAPIResponse(like);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }




    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Like.class, likeRepository);
    }

    @Override
    public APIResponse createBatch(List<Like> likes) {
        List<Like> createdLikes = new ArrayList<>();
        for (Like like : likes) {
            Like createdLike = likeRepository.save(like);
            createdLikes.add(createdLike);
        }
        return new SuccessAPIResponse(createdLikes);
    }

    @Override
    public APIResponse getListYouLike(UserPrincipal userPrincipal) {
        List<Like> listYouLike = likeRepository.findByCreatedBy(userPrincipal.getEmail());
        if(listYouLike.size() == 0) {
            new SuccessAPIResponse("You haven't liked anyone yet");
        }

        List<Notification> peopleYouLike = listYouLike.stream()
                .map(accountMapper::notificationFromLike)
                .collect(Collectors.toList());
        return new SuccessAPIResponse("Get the list of people you liked successfully", peopleYouLike);
    }

    @Override
    public APIResponse getListLikeYou(UserPrincipal userPrincipal) {
        List<Like> listLikeYou = likeRepository.findByAccount(userPrincipal.toAccount());
        if(listLikeYou.size() == 0) {
            return new SuccessAPIResponse("You don't have any liked yet");
        }
        List<Notification> peopleLikeYou = listLikeYou.stream()
                .map(accountMapper::notificationFromLike)
                .collect(Collectors.toList());
        return new SuccessAPIResponse("Get the list of people liked you successfully", peopleLikeYou);
    }
}
