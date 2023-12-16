package com.app.service.serviceImpl;

import com.app.entity.Blog;
import com.app.entity.BlogComment;
import com.app.entity.BlogLike;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.AccountRepository;
import com.app.repository.BlogCommentRepository;
import com.app.repository.BlogLikeRepository;
import com.app.repository.BlogRepository;
import com.app.security.UserPrincipal;
import com.app.service.BlogInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogInteractionServiceImpl implements BlogInteractionService {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    @Autowired
    BlogLikeRepository blogLikeRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AccountRepository accountRepository;
    @Override
    public APIResponse addComment(Integer blogId, String content) {
        Optional<Blog> existBlog = blogRepository.findById(blogId);
        if (existBlog.isEmpty()) {
            return new FailureAPIResponse("This blogId does not exist");
        }
        if(content.trim().length() == 0) {
            return new FailureAPIResponse("Your comment must not empty");
        }
        BlogComment blogComment = new BlogComment();
        blogComment.setBlog(existBlog.get());
        blogComment.setContent(content);
        try {
            blogCommentRepository.save(blogComment);
            return new SuccessAPIResponse("Comment successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }

    @Override
    public APIResponse deleteComment(Integer blogCommentId) {
        Optional<BlogComment> existBlogComment = blogCommentRepository.findById(blogCommentId);
        if (existBlogComment.isEmpty()) {
            return new FailureAPIResponse("This blogCommentId does not exist");
        }
        try {
            blogCommentRepository.delete(existBlogComment.get());
            return new SuccessAPIResponse("Remove comment successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }

    @Override
    public APIResponse likeBlog(Integer blogId, UserPrincipal userPrincipal) {
        Optional<Blog> existBlog = blogRepository.findById(blogId);
        if (existBlog.isEmpty()) {
            return new FailureAPIResponse("This blogId does not exist");
        }
        BlogLike existBlogLike = blogLikeRepository.findByBlogAndCreatedBy(existBlog.get(), userPrincipal.getEmail());
        if (existBlogLike != null) {
            return new FailureAPIResponse("You have already liked this blog");
        }

        BlogLike blogLike = new BlogLike();
        blogLike.setBlog(existBlog.get());
        try {
            blogLikeRepository.save(blogLike);
            return new SuccessAPIResponse("Like blog successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }

    @Override
    public APIResponse unlikeBlog(Integer blogId, UserPrincipal userPrincipal) {
        Optional<Blog> existBlog = blogRepository.findById(blogId);
        if (existBlog.isEmpty()) {
            return new FailureAPIResponse("This blogId does not exist");
        }
        BlogLike existBlogLike = blogLikeRepository.findByBlogAndCreatedBy(existBlog.get(), userPrincipal.getEmail());
        if (existBlogLike == null) {
            return new FailureAPIResponse("You have already unliked this blog");
        }
        try {
            blogLikeRepository.delete(existBlogLike);
            return new SuccessAPIResponse("Unlike blog successfully");
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }
}


















