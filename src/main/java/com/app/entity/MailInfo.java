package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	String otp;
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body = "test";
	String[] attachments;

	public MailInfo(String to, String subject, String body) {
		this.from = "Travel_Social <travelsocial2023@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		}
	public MailInfo(String otp, String from) {
		this.otp = otp;
		this.from = from;

	}

}
