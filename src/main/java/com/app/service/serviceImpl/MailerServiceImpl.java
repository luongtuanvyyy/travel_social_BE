package com.app.service.serviceImpl;

import com.app.entity.Account;
import com.app.entity.MailInfo;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.repository.AccountRepository;
import com.app.service.MailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@Service

public class MailerServiceImpl implements MailerService {

	@Autowired
	JavaMailSender sender;
	@Autowired
	AccountRepository accountRepository;
	private String otpCheck;
	private LocalDateTime otpGeneratedTime;
	@Override
	public void send(MailInfo mail) throws MessagingException {
		// Tạo message
		MimeMessage message = sender.createMimeMessage();

		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(mail.getBody(), true);
		helper.setReplyTo(mail.getFrom());
		String[] cc = mail.getCc();
		if (cc != null && cc.length > 0) {
			helper.setCc(cc);
		}

		String[] bcc = mail.getBcc();
		if (bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}

		String[] attachments = mail.getAttachments();
		if (attachments != null && attachments.length > 0) {
			for (String path : attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}

		sender.send(message);

	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		this.send(new MailInfo(to, subject, body));
	}

	@Override
	public APIResponse OTP(String gmail, String status) throws MessagingException {
		Account exists = accountRepository.findByGmail(gmail);
//		System.out.println(exists.getEmail());
		if (exists != null && status.equals("Register")) {
			return new APIResponse(false, "Account exists");
		}
		try {
			String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			String subject = "Travel_Social";
			String body = "Your OTP: ";
			StringBuilder otp = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 6; i++) {
				int index = random.nextInt(characters.length());
				otp.append(characters.charAt(index));
			}

			this.send(gmail, subject, body + otp.toString());
			otpCheck = otp.toString();
			otpGeneratedTime = LocalDateTime.now(); // Record the time when OTP is generated

			return new APIResponse(new MailInfo(otp.toString(), gmail));
		} catch (Exception ex) {
			return new FailureAPIResponse(ex.getMessage());
		}
	}

	@Scheduled(fixedDelay = 60000) // 1 minute delay
	public void resetOtp() {
		otpCheck = null;
		otpGeneratedTime = null;
	}


	@Override
	public APIResponse CheckOTP(String otp) {
		try{
			if (!otp.equals(otpCheck)){
				return new APIResponse(false, "OTP Not Equal");
			}
			return new APIResponse(true, "OTP Equal");
		} catch (Exception ex) {
			return new FailureAPIResponse(ex.getMessage());
		}
	}
	@Override
	public String sendOTP(String gmail) throws MessagingException {
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String subject = "Travel_Social";
		String body = "Your OTP: ";
		StringBuilder otp = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt(characters.length());
			otp.append(characters.charAt(index));
		}
		this.send(gmail, subject, body+otp.toString());

		return otp.toString();
	}


}
