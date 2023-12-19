package com.app.service;

import com.app.payload.request.AccountQueryParam;
import com.app.payload.response.APIResponse;

public interface AccountService {
    APIResponse filterAccount(AccountQueryParam accountQueryParam);

    APIResponse getImageByGmail(String gmail, AccountQueryParam accountQueryParam);

    APIResponse blockAccount(Integer id);


    APIResponse removeAccount(Integer id);

    APIResponse getAccountDetail(Integer id, AccountQueryParam accountQueryParam);


}
