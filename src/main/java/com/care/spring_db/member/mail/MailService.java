package com.care.spring_db.member.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired JavaMailSender mailSender;//JavaMailSender config팩키지에서 만든 이메일 연결 속성을 해놓음
	
	public void sendMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();//
		try {
			MimeMessageHelper msgHelper = new MimeMessageHelper(message, true, "UTF-8");
			//헬퍼를 이용해서 email 연결 정보를 입력하는게 더 편리함
			//데이터의 양이 많다면 두번쨰 영역을 true를 하면된다 == multipart
			//마지막은 인코딩 형식 == utf-8
			
			msgHelper.setSubject(subject); //이메일 제목
			msgHelper.setText(content); //이메일 내요
			msgHelper.setTo(to); //이메일 수신자
			
			
			mailSender.send(message);//메일을 송부
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
	}
	
	
}
