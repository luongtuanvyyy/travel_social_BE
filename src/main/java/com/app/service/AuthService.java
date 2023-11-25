package com.app.service;

import com.app.entity.Account;
import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    APIResponse login(Account account);
    APIResponse register(Account account);
    APIResponse registerCompany(Account account);
    APIResponse register (Account account, MultipartFile file);
    APIResponse registerCompany (Account account, MultipartFile file);
    APIResponse updateInformation (UserPrincipal userPrincipal,Account account, MultipartFile file);
    APIResponse logout();

    APIResponse googleToken(String token) throws Exception;
}
