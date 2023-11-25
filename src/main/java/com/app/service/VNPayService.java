package com.app.service;

import javax.servlet.http.HttpServletRequest;

public interface VNPayService {
    public String createOder(int total, String orderInfor, String urlReturn);

    public int orderReturn(HttpServletRequest request);

}
