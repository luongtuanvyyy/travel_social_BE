package com.app.api;

import com.app.entity.Favorite;
import com.app.entity.Follow;
import com.app.payload.request.FollowQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.FollowServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowApi {
    @Autowired
    FollowServices followServices;

    @GetMapping("/user/follows")
    public ResponseEntity<?> getAllFollow(FollowQueryParam followQueryParam) {
        return ResponseEntity.ok(followServices.filterFollow(followQueryParam));
    }

    @PutMapping("/user/follows")
    public ResponseEntity<?> updateFollow(@RequestPart(name = "follow") Follow follow) {
        APIResponse response = followServices.update(follow);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/follows/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = followServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/follows/batch")
    public ResponseEntity<?> createFollowsBatch(@RequestBody List<Follow> follows) {
        APIResponse response = followServices.createBatch(follows);
        return ResponseEntity.ok().body(response);
    }

    //======

    @PostMapping("/user/follows")
    public ResponseEntity<?> createFollow(@RequestParam("userId") Integer userId, @CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = followServices.create(userId, userPrincipal);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/user/follows")
    public ResponseEntity<?> deleteFollow(@RequestParam("userId") Integer userId, @CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = followServices.delete(userId, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/follows/list-you-follow")
    public ResponseEntity<?> getListYouFollow(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(followServices.getListYouFollow(userPrincipal));
    }

    @GetMapping("/user/follows/list-follow-you")
    public ResponseEntity<?> getListFollowYou(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(followServices.getListFollowYou(userPrincipal));
    }

    @GetMapping("/user/follows/top-follow")
    public ResponseEntity<?> getTopFollow() {
        return ResponseEntity.ok(followServices.getTopFollower());
    }
}
