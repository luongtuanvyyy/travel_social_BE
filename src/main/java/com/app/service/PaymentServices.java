package com.app.service;


import com.app.entity.Payment;
import com.app.payload.request.PaymentQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PaymentServices {
    APIResponse filterPayment(PaymentQueryParam paymentQueryParam);

    APIResponse create(Payment payment);
    APIResponse update(Payment payment);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
}
