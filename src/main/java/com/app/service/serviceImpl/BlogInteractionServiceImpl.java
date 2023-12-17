package com.app.service.serviceImpl;

import com.app.dto.AccountDto2;
import com.app.entity.Blog;
import com.app.entity.BlogComment;
import com.app.entity.BlogLike;
import com.app.mapper.AccountMapper;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private AccountMapper accountMapper;
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
    public APIResponse replyComment(Integer parentCommentId, String content) {
        Optional<BlogComment> parentComment = blogCommentRepository.findById(parentCommentId);
        if (parentComment.isEmpty()) {
            return new FailureAPIResponse("This parentCommentId does not exist");
        }

        BlogComment reply = new BlogComment();
        reply.setParentComment(parentComment.get());
        reply.setContent(content);

        try {
            blogCommentRepository.save(reply);
            return new SuccessAPIResponse("Reply added successfully");
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
    public APIResponse updateComment(Integer blogCommentId, String content) {
        Optional<BlogComment> existBlogComment = blogCommentRepository.findById(blogCommentId);
        if (existBlogComment.isEmpty()) {
            return new FailureAPIResponse("This blogCommentId does not exist");
        }
        BlogComment blogComment = existBlogComment.get();
        blogComment.setContent(content);
        try {
            blogCommentRepository.save(blogComment);
            return new SuccessAPIResponse("Update comment successfully");
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

    @Override
    public APIResponse getListLikeYourBlog(UserPrincipal userPrincipal) {
        try {
            List<Blog> blogsCreatedByUser = blogRepository.findByCreatedBy(userPrincipal.getEmail());
            List<AccountDto2> peopleLikedYourBlogs = blogsCreatedByUser.stream()
                    .flatMap(blog -> {
                        List<AccountDto2> likers = blogLikeRepository.findByBlog(blog).stream()
                                .map(blogLike -> accountMapper.accountDto2(accountRepository.findByEmail(blogLike.getCreatedBy()).get()))
                                .collect(Collectors.toList());
                        return likers.stream();
                    })
                    .distinct()
                    .collect(Collectors.toList());

            if (peopleLikedYourBlogs.isEmpty()) {
                return new SuccessAPIResponse("No one has liked your blog post yet");
            }

            return new SuccessAPIResponse("Get the list of people who liked your blogs successfully", peopleLikedYourBlogs);
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }


    @Override
    public APIResponse getListCommentYourBlog(UserPrincipal userPrincipal) {
        try {
            List<Blog> blogsCreatedByUser = blogRepository.findByCreatedBy(userPrincipal.getEmail());
            List<AccountDto2> peopleLikedYourBlogs = blogsCreatedByUser.stream()
                    .flatMap(blog -> {
                        List<AccountDto2> likers = blogCommentRepository.findByBlog(blog).stream()
                                .map(blogLike -> accountMapper.accountDto2(accountRepository.findByEmail(blogLike.getCreatedBy()).get()))
                                .collect(Collectors.toList());
                        return likers.stream();
                    })
                    .distinct()
                    .collect(Collectors.toList());

            if (peopleLikedYourBlogs.isEmpty()) {
                return new SuccessAPIResponse("No one has Comment your blog post yet");
            }

            return new SuccessAPIResponse("Get the list of people who Comment your blogs successfully", peopleLikedYourBlogs);
        } catch (Exception e) {
            return new FailureAPIResponse("Error: " + e.getMessage());
        }
    }
}


















