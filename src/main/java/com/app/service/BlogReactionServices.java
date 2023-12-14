package com.app.service;

import com.app.entity.Blog;
import com.app.entity.BlogReaction;
import com.app.entity.Voucher;
import com.app.payload.request.BlogReactionQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BlogReactionServices {

    APIResponse filterBlogReaction(BlogReactionQueryParam blogReactionQueryParam);

    APIResponse create(BlogReaction blogReaction);

    APIResponse update(BlogReaction blogReaction);
    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<BlogReaction> blogReactions);
}
