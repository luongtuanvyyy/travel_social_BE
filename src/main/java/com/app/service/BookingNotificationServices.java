package com.app.service;

import com.app.entity.BookingNotification;
import com.app.entity.Voucher;
import com.app.payload.request.BookingNotificationQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookingNotificationServices {
    APIResponse filterBookingNotification(BookingNotificationQueryParam bookingNotificationQueryParam);

    APIResponse create(BookingNotification bookingNotification);

    APIResponse update(BookingNotification bookingNotification);

    APIResponse delete(Integer id);

    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<BookingNotification> bookingNotifications);
}
