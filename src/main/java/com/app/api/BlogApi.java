package com.app.api;

import com.app.entity.Blog;
import com.app.entity.Voucher;
import com.app.payload.request.BlogQueryParam;
import com.app.payload.response.APIResponse;
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

    @GetMapping("/public/blogs")
    public ResponseEntity<?> getAllBlog(BlogQueryParam blogQueryParam) {
        return ResponseEntity.ok(blogServices.filterBlog(blogQueryParam));
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
    public ResponseEntity<?> createBlog(@RequestPart(name = "blog") Blog blog,
                                         HttpServletRequest request) {
        APIResponse response = blogServices.create(blog,request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/user/blogs")
    public ResponseEntity<?> updateBlog(@RequestPart(name = "blog") Blog blog,
                                        HttpServletRequest request) {
        APIResponse response = blogServices.update(blog,request);
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

}
