package com.app.api;

import com.app.entity.BlogReaction;
import com.app.payload.request.BlogReactionQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.BlogReactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogReactionApi {
    @Autowired
    BlogReactionServices blogReactionServices;


    @GetMapping("/public/blog-reactions")
    public ResponseEntity<?> filterBlogReaction(BlogReactionQueryParam blogReactionQueryParam) {
        return ResponseEntity.ok(blogReactionServices.filterBlogReaction(blogReactionQueryParam));

    }

    @PostMapping("/user/blog-reactions")
    public ResponseEntity<?> createBlogReaction(@RequestPart(name = "blog_reaction") BlogReaction blogReaction) {
        APIResponse response = blogReactionServices.create(blogReaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/user/blog-reactions")
    public ResponseEntity<?> updateBlogReaction(@RequestPart(name = "blog_reaction") BlogReaction blogReaction) {
        APIResponse response = blogReactionServices.update(blogReaction);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/user/blog-reactions")
    public ResponseEntity<?> deleteBlogReaction(@RequestParam("id") Integer id) {
        APIResponse response = blogReactionServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/blog-reactions/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = blogReactionServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/user/blog-reactions/batch")
    public ResponseEntity<?> createBlogReactionsBatch(@RequestBody List<BlogReaction> blogReactions) {
        APIResponse response = blogReactionServices.createBatch(blogReactions);
        return ResponseEntity.ok().body(response);
    }
}
