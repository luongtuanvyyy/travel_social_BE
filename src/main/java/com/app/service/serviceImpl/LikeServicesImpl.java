package com.app.service.serviceImpl;

import com.app.entity.Blog;
import com.app.entity.Follow;
import com.app.entity.Like;
import com.app.payload.request.LikeQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.LikeRepository;
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

    @Override
    public APIResponse filterLike(LikeQueryParam likeQueryParam) {
        try {
        Specification<Like> spec = likeSpecification.getLikeSpecitification(likeQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(likeQueryParam);
        Page<Like> response = likeRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse create(Like like) {
        try {
        like = likeRepository.save(like);
        return new SuccessAPIResponse(like);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
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
    public APIResponse delete(Integer id) {
        try {
            likeRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
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
}
