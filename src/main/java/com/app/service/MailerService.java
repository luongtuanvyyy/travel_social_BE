package com.app.service;


import com.app.entity.MailInfo;
import com.app.payload.response.APIResponse;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Service
public interface MailerService {
	void send(MailInfo mail) throws MessagingException;

	void send(String to, String subject, String body) throws MessagingException;

	APIResponse OTP(String gmail) throws MessagingException;

	String sendOTP(String gmail) throws MessagingException;
	
	
}
