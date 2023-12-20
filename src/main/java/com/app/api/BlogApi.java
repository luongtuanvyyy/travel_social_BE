package com.app.api;

import com.app.dto.BlogUpdateDto;
import com.app.entity.Blog;
import com.app.entity.BlogReaction;
import com.app.entity.Voucher;
import com.app.payload.request.BaseQueryRequest;
import com.app.payload.request.BlogModalQueryParam;
import com.app.payload.request.BlogQueryParam;
import com.app.payload.request.TourQueryParam;
import com.app.payload.response.APIResponse;
import com.app.repository.BlogReactionRepository;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.BlogInteractionService;
import com.app.service.BlogReactionServices;
import com.app.service.BlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogApi {
    @Autowired
    BlogServices blogServices;
    @Autowired
    BlogReactionRepository blogReactionRepository;
    @Autowired
    BlogInteractionService blogInteractionService;
    @Autowired
    BlogReactionServices blogReactionServices;

    @PostMapping("/user/blogs/share")
    public ResponseEntity<?> getShare(@RequestParam("id") Integer id, BlogQueryParam blogQueryParam) {
        return ResponseEntity.ok(blogServices.getAccountByBlogId(id, blogQueryParam));
    }

    @DeleteMapping("/user/blogs/share")
    public ResponseEntity<?> getDeleteShare(@RequestParam("id") Integer id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(blogReactionServices.deleteShare(id, userPrincipal));
    }

    @PostMapping("/user/blogs/like")
    public ResponseEntity<?> cretaLike(@RequestParam("id") Integer id, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(blogReactionServices.createLike(id, userPrincipal));
    }

    @GetMapping("/public/blogs")
    public ResponseEntity<?> getAllBlog(BlogModalQueryParam blogModalQueryParam) {
        return ResponseEntity.ok(blogServices.getAllBlogWithAccount(blogModalQueryParam));
    }

    @GetMapping("/public/blog")
    public ResponseEntity<?> getBlog(@RequestParam("id") Integer id, BlogModalQueryParam blogModalQueryParam) {
        return ResponseEntity.ok(blogServices.getBlogAccount(id, blogModalQueryParam));
    }

    @GetMapping("/public/blog/comment")
    public ResponseEntity<?> getBlogComment(@RequestParam("id") Integer id, BlogModalQueryParam blogModalQueryParam) {
        return ResponseEntity.ok(blogServices.getBlogComment(id, blogModalQueryParam));
    }

    @GetMapping("/public/blogs/notSeen")
    public ResponseEntity<?> filterBlogNotSeen(BlogQueryParam blogQueryParam) {
        return ResponseEntity.ok(blogServices.filterBlogNotSeen(blogQueryParam));
    }

    @GetMapping("/public/blogs/least")
    public ResponseEntity<?> filterLeastBlog(BlogQueryParam blogQueryParam) {
        return ResponseEntity.ok(blogServices.filterLeastBlog(blogQueryParam));
    }

    @PostMapping("/user/blogs")
    public ResponseEntity<?> createBlog(@RequestPart(name = "blog") Blog blog, @RequestPart(name = "image") MultipartFile image) {
        APIResponse response = blogServices.create(blog, image);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/user/blogs")
    public ResponseEntity<?> updateBlog(@RequestPart(name = "blog") BlogUpdateDto blog, @RequestPart(name = "image") @Nullable MultipartFile image) {
        APIResponse response = blogServices.update(blog, image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/blogs")
    public ResponseEntity<?> deleteBlog(@RequestParam("id") Integer id) {
        APIResponse response = blogServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/blogs/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = blogServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/blogs/batch")
    public ResponseEntity<?> createBlogsBatch(@RequestBody List<Blog> blogs) {
        APIResponse response = blogServices.createBatch(blogs);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/blogs/comments")
    public ResponseEntity<?> addComment(@RequestParam("blogId") Integer blogId, @RequestParam("content") String content) {
        APIResponse response = blogInteractionService.addComment(blogId, content);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/blogs/replies")
    public ResponseEntity<?> replyComment(@RequestParam("parentCommentId") Integer parentCommentId, @RequestParam("content") String content) {
        APIResponse response = blogInteractionService.replyComment(parentCommentId, content);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/user/blogs/comments")
    public ResponseEntity<?> deleteComment(@RequestParam("blogCommentId") Integer blogCommentId) {
        APIResponse response = blogInteractionService.deleteComment(blogCommentId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/user/blogs/comments")
    public ResponseEntity<?> updateComment(@RequestParam("blogCommentId") Integer blogCommentId, @RequestParam("content") String content) {
        APIResponse response = blogInteractionService.updateComment(blogCommentId, content);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/blogs/likes")
    public ResponseEntity<?> addComment(@RequestParam("blogId") Integer blogId, @CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = blogInteractionService.likeBlog(blogId, userPrincipal);
        return ResponseEntity.ok().body(response);
    }

//    @DeleteMapping("/user/blogs/likes")
//    public ResponseEntity<?> unlikeBlog(@RequestParam("blogId") Integer blogId, @CurrentUser UserPrincipal userPrincipal) {
//        APIResponse response = blogInteractionService.unlikeBlog(blogId, userPrincipal);
//        return ResponseEntity.ok().body(response);
//    }


    @GetMapping("/public/blogs/comments")
    public ResponseEntity<?> getComment(@RequestParam("blogId") Integer blogId, BaseQueryRequest baseQueryRequest) {
        APIResponse response = blogServices.getComment(blogId, baseQueryRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/blogs/list-likes")
    public ResponseEntity<?> getListLikeYourBlog(@CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = blogInteractionService.getListLikeYourBlog(userPrincipal);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/blogs/list-comments")
    public ResponseEntity<?> getComment(@CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = blogInteractionService.getListCommentYourBlog(userPrincipal);
        return ResponseEntity.ok().body(response);
    }

}
