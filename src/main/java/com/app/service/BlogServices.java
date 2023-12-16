package com.app.service;

import com.app.entity.Blog;
import com.app.entity.Voucher;
import com.app.payload.request.BlogModalQueryParam;
import com.app.payload.request.BlogQueryParam;
import com.app.payload.request.TourQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface BlogServices {
    List<Blog> findAll();
    Optional<Blog> findById(Integer id);

    APIResponse filterBlog(BlogQueryParam blogQueryParam);

    APIResponse filterBlogNotSeen(BlogQueryParam blogQueryParam);

    APIResponse filterLeastBlog(BlogQueryParam blogQueryParam);

    APIResponse create(Blog blog, HttpServletRequest request);

    APIResponse update(Blog blog, HttpServletRequest request);

    APIResponse delete(Integer id);

    List<Blog> findByTitle(BlogQueryParam blogQueryParam);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Blog> blogs);

    APIResponse getAccountByBlogId(Integer id, BlogQueryParam blogQueryParam);

    APIResponse getAllBlogWithAccount(BlogModalQueryParam blogModalQueryParam);


}
