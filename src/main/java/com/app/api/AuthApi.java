package com.app.api;


import com.app.entity.Account;
import com.app.payload.response.APIResponse;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.AuthService;
import com.app.service.serviceImpl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AuthApi {
    @Autowired
    AuthService authService;
    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> registerWithoutAvatar(
            @RequestBody Account account) {
        APIResponse response = authService.register(account);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/auth/register-company")
    public ResponseEntity<?> registerCompanyWithoutAvatar(
            @RequestBody Account account) {
        APIResponse response = authService.registerCompany(account);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/auth/register-company-avatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerCompanyWithAvatar(
            @RequestPart(name = "account") Account account,
            @RequestPart(name = "file") MultipartFile file) {
        APIResponse response = authService.register(account, file);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/auth/login/GGFBToken")
    public ResponseEntity<?> googleToken(@RequestParam("token") String token) throws Exception {
        APIResponse response =  authService.googleToken(token);
        return ResponseEntity.ok(response);
    }
    @PostMapping(path = "/auth/register-avatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerWithAvatar(
            @RequestPart(name = "account") Account account,
            @RequestPart(name = "file") MultipartFile file) {
        APIResponse response = authService.register(account, file);
        return ResponseEntity.ok(response);
    }


    @PutMapping(path = "/account-information", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateInformation(
            @RequestPart(name = "account") Account account,
            @RequestPart(name = "file") @Nullable MultipartFile file,
            @CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = authService.updateInformation(userPrincipal, account, file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        APIResponse response = authService.login(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        APIResponse response = authService.logout();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok("null");
    }

}
