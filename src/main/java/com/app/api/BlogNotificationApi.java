package com.app.api;

import com.app.entity.Blog;
import com.app.entity.BlogNotification;
import com.app.payload.request.BlogNotificationQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.BlogNotificationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogNotificationApi {
    @Autowired
    BlogNotificationServices blogNotificationServices;

    //web socket
    @GetMapping("/user/blog-notifications")
    public ResponseEntity<?> getAllBlogNotification(BlogNotificationQueryParam blogNotificationQueryParam) {
        return ResponseEntity.ok(blogNotificationServices.filterBlogNotification(blogNotificationQueryParam));
    }

    @GetMapping("/user/getAllBlogNotification")
    public ResponseEntity<?> getAllBlogNotification(@CurrentUser UserPrincipal userPrincipal, BlogNotificationQueryParam blogNotificationQueryParam) {
        String email = userPrincipal.getEmail();
        System.out.println(email);
        try {
            APIResponse response = blogNotificationServices.getBlogNotificationByEmail(email, blogNotificationQueryParam);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/user/blog-notifications")
    public ResponseEntity<?> createFavorite(@RequestPart(name = "blogNotification") BlogNotification blogNotification) {
        APIResponse response = blogNotificationServices.create(blogNotification);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/user/blog-notifications")
    public ResponseEntity<?> updateFavorite(@RequestPart(name = "blogNotification") BlogNotification blogNotification) {
        APIResponse response = blogNotificationServices.update(blogNotification);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/blog-notifications")
    public ResponseEntity<?> deleteFavorite(@RequestParam("id") Integer id) {
        APIResponse response = blogNotificationServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/blog-notifications/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = blogNotificationServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/blog-notifications/batch")
    public ResponseEntity<?> createBlogNotificationsBatch(@RequestBody List<BlogNotification> blogNotifications) {
        APIResponse response = blogNotificationServices.createBatch(blogNotifications);
        return ResponseEntity.ok().body(response);
    }
}
