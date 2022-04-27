package com.care.spring_db.member.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


//이메일을 이용한 메일 인증 연습
@Configuration
public class Mailonfig {
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl jms = new JavaMailSenderImpl();
		//접속 정보 설정
		jms.setHost("smtp.gmail.com");//어느 이메일을 이용하여 인증용 메일을 보낼 것이냐?
		//회사처럼 사설 메일 서버가 없기에 구글 메일을 이용
		jms.setPort(587);//구글메일에서 사용하는 포트 명은 지정되어 있다.
		jms.setUsername("ksyrang.w@gmail.com");//인증번호를 보낼 이메일 번호
		jms.setPassword("rlatlfkd!30425");//위 이메일 번호의 비밀번호
		
		//연결을 위한 접속 관리 정보
		Properties pro = new Properties();
		pro.setProperty("mail.transport.protocol", "smtp");
		pro.setProperty("mail.smtp.auth", "true");
		pro.setProperty("mail.smtp.starttls.enable", "true");
		jms.setJavaMailProperties(pro);//Properties 형태의 자료형을 속성에 넣어야 함
		return jms; 
	}
}
