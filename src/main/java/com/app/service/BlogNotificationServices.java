package com.app.service;

import com.app.entity.BlogNotification;
import com.app.entity.Voucher;
import com.app.payload.request.BlogNotificationQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogNotificationServices {

    APIResponse filterBlogNotification(BlogNotificationQueryParam blogQueryParam);
    APIResponse create(BlogNotification blogNotification);
    APIResponse update(BlogNotification blogNotification);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<BlogNotification> blogNotifications);
}
