package com.app.api;

import com.app.entity.Payment;
import com.app.payload.request.PaymentQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.PaymentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PaymentApi {
    @Autowired
    PaymentServices paymentServices;
    @GetMapping("/public/payment")
    public ResponseEntity<?> filterPayment(PaymentQueryParam paymentQueryParam) {
        return ResponseEntity.ok(paymentServices.filterPayment(paymentQueryParam));
    }

    @PostMapping("/company/payment")
    public ResponseEntity<?> createPayment(@RequestPart(name = "payment") Payment payment) {
        APIResponse response = paymentServices.create(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/company/payment")
    public ResponseEntity<?> updatePayment(@RequestPart(name = "payment") Payment payment) {
        APIResponse response = paymentServices.update(payment);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/payment")
    public ResponseEntity<?> deletePayment(@RequestParam("id") Integer id) {
        APIResponse response = paymentServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/payment/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = paymentServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
}
