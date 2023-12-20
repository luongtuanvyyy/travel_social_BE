package com.app.service;

import com.app.payload.response.APIResponse;

import javax.servlet.http.HttpServletRequest;

public interface VNPayService {
     APIResponse createOder(int total, String orderInfor, String urlReturn);

     int orderReturn(HttpServletRequest request);

}
