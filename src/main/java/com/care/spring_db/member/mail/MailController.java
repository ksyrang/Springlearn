package com.care.spring_db.member.mail;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

	/*
	 * @ResponseBody
	 * 
	 * @PostMapping(value = "member/sendAuth", produces =
	 * "text/html; charset=UTF-8") public String sendAuth(@RequestBody (required =
	 * false)String email) { System.out.println("인증 보내낼 이메일: " + email);
	 * if(email.isEmpty() || email == null) { return "내용 없음"; } return email; }
	 */
	
	@Autowired private MailService mailSvc;
	@Autowired private HttpSession session;
	
	@ResponseBody
	@PostMapping(value = "member/sendAuth", produces = "text/html; charset=UTF-8")
	public String sendAuth(@RequestBody (required = false)String email) {
//		System.out.println("인증 보내낼 이메일: " + email);
		//email의 존재 여부
		if(email == null) return "이메일을 입력하세요.";
		/*6자리로 구성된 숫자로 인증번호 만들기
		 * 문자열로 송신, 0~9의 문자로 구성할 것
		 * random 함수 이용!
		*/
		Random random = new Random();//랜덤클래스 개체 인스턴스
		String number="";
		for(int i=0;i<6;i++) {//원하는 갯수의 동안 loop
			int tmp = random.nextInt(9);//nextInt(x); 0~x까지의 숫자중 하나를 리턴함
			number+= Integer.toString(tmp);
		}
		//String number2 = String.format("%06d", random.nextInt(1000000));
		
		mailSvc.sendMail(email,"[인증번호]",number);
		session.setAttribute("Auth_Num", number);
		
		return "송신 완료";
	}
	
	@ResponseBody
	@PostMapping(value = "member/checkAuth", produces = "text/html; charset=UTF-8")
	public String checkAuth(@RequestBody (required = false)Map<String,String> map ) {
//		System.out.println("고객이 인증 번호 : "+ map.get("check_num"));
		String AuthNum = (String)session.getAttribute("Auth_Num");
		String CheckNum = map.get("check_num");
		if(CheckNum.isEmpty() || CheckNum == null) return "번호를 입력하세요.";
		//sendAuth 생성한 랜덤 인증번호와 고객이 입력한 번호와의 일치 여부 확인 필요
		
		if(AuthNum.isEmpty()|| AuthNum == null) return "인증번호를 생성하세요.";
		if(AuthNum.equals(CheckNum)) {
			session.setAttribute("authState", true);
			return "인증성공";
		}else {
			return "인증실패";		
		}

	}
		
}
