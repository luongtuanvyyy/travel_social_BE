package com.app.service;

import com.app.entity.BookingCancel;
import com.app.entity.Voucher;
import com.app.payload.request.BookingCancelQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookingCancelServices {

    APIResponse filterBookingCancel (BookingCancelQueryParam bookingCancelQueryParam);
    APIResponse create(BookingCancel bookingCancel);
    APIResponse update(BookingCancel bookingCancel);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<BookingCancel> bookingCancels);
}
