package com.app.api;

import com.app.payload.response.APIResponse;
import com.app.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
public class OtpApi {
    @Autowired
    MailerService mailerService;

    @GetMapping("/public/otp")
    public ResponseEntity<?> getOTP(@RequestParam("gmail") String gmail,@RequestParam("status")  String Status) throws MessagingException {
        APIResponse response = mailerService.OTP(gmail, Status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/public/otpCheck")
    public ResponseEntity<?> checkOTP(@RequestParam("otp") String otp) {
        APIResponse response = mailerService.CheckOTP(otp);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
