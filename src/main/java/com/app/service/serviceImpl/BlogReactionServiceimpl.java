package com.app.service.serviceImpl;

import com.app.entity.BlogNotification;
import com.app.entity.BlogReaction;
import com.app.entity.Voucher;
import com.app.payload.request.BlogReactionQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.BlogReactionRepository;
import com.app.service.BlogReactionServices;
import com.app.speficication.BlogReactionSpecification;
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
public class BlogReactionServiceimpl implements BlogReactionServices {
    @Autowired
    BlogReactionSpecification blogReactionSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    BlogReactionRepository blogReactionRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterBlogReaction(BlogReactionQueryParam blogReactionQueryParam) {
        Specification<BlogReaction> spec = blogReactionSpecification.getBlogReactionSpecitification(blogReactionQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(blogReactionQueryParam);
        Page<BlogReaction> response = blogReactionRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }
    @Override
    public APIResponse create(BlogReaction blogReaction) {
        blogReaction = blogReactionRepository.save(blogReaction);
        return new SuccessAPIResponse(blogReaction);
    }

    @Override
    public APIResponse update(BlogReaction blogReaction) {
        if(blogReaction == null){
            return  new FailureAPIResponse("Blog Reaction id is required!");
        }
        BlogReaction exists = blogReactionRepository.findById(blogReaction.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find blog reaction with id: "+blogReaction.getId());
        }
        blogReaction = blogReactionRepository.save(blogReaction);
        return new SuccessAPIResponse(blogReaction);
    }


    @Override
    public APIResponse delete(Integer id) {
        try {
            blogReactionRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, BlogReaction.class, blogReactionRepository);
    }

    @Override
    public APIResponse createBatch(List<BlogReaction> blogReactions) {
        List<BlogReaction> createdBlogReactions = new ArrayList<>();
        for (BlogReaction blogReaction : blogReactions) {
            BlogReaction createdBlogReaction = blogReactionRepository.save(blogReaction);
            createdBlogReactions.add(createdBlogReaction);
        }
        return new SuccessAPIResponse(createdBlogReactions);
    }
}
