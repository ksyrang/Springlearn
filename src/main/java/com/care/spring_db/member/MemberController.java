package com.care.spring_db.member;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MemberController {
	@Autowired HttpSession session;
	@Autowired private MemberService memberService;//스프링에서 관리하고 있는 클래스가 있다면 자동으로 연결해 달라! = @Autowired
	//즉 객체의 제어를 스프링에게 넘겨서 사용하겠다.(결국 new xxxx();하는 것과 동일하다.)
	private final static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value = "member/index")
	public void index() {
		logger.info("member/index의 실행 결과 리턴");
	}
	
	@GetMapping(value = "member/member")
	public void member() {
		logger.info("member/member의 실행 결과 리턴");
	}
	
	@ResponseBody
	@PostMapping(value="member/idcheck", produces = "text/html; charset=UTF-8")
	public String idCheck(@RequestBody (required = false)String id) {
		System.out.println("Post method idCheck");
		if(id == null||id.isEmpty()){
			return "아이디를 입력하세요";
		}else {
			if(memberService.checkId(id)) {
				return "등록 가능";
			}
			return "등록된 아이디";
		}
	}
	
	//오버로딩을 이용한 메소드 함수 처리
	@RequestMapping(value = "member/member", method=RequestMethod.POST)
	public String member(MemberVO memberVO, Model model) {//memberVO = memberDTO상속받고 비번확인 변수 추가
		logger.info("post member/member의 실행 결과 리턴");	
		int result = memberService.insert(memberVO);
		switch(result) {
			case 0:{
				model.addAttribute("msg", "가입실패");
				return "member/member";
			}	
			case 1:{
				model.addAttribute("msg", "가입 완료");
				return "member/login";
			}
			case 2:{
				model.addAttribute("msg", "입력 정보 이상");
				return "member/member";
			}
			case 3:{
				model.addAttribute("msg", "중복된 아이디");
				return "member/member";
			}
			case 8:{
				model.addAttribute("msg", "이메일 인증을 진행해 주세요.");
				return "member/member";
			}
		}
		return "redirect:index";
	}
	@GetMapping(value = "member/login")
	public void login() {
		logger.info("@GetMapping member/login의 실행 결과 리턴");
		
	}
	@PostMapping(value = "member/login")
	public String login(MemberDTO member, Model model) {
		logger.info("@PostMapping member/login의 실행 결과 리턴");
		
		int result = memberService.selectId(member);
		
		if(result == 1) {
			model.addAttribute("msg",session.getAttribute("id")+"님 환영합니다.");
			return "member/index";
		}else if(result == 2) {
			model.addAttribute("msg", "필수 입력 사항 미입력");
			return "member/login";
		}else if(result == 3) {
			model.addAttribute("msg", "미 등록 아이디");
			return "member/login";
		}else {
			model.addAttribute("msg", "입력 정보 불일치");
			return "member/login";
		}
		
	}
	@GetMapping(value = "member/logout")
	public String logout() {
		logger.info("member/logout의 실행 결과 리턴");
		session.invalidate();
		return "redirect:index";
	}
	@GetMapping(value = "member/list")
	public void list(Model model) {
		logger.info("member/list의 실행 결과 리턴");
		
		model.addAttribute("list", memberService.sltAll());
	}
	@GetMapping(value = "member/update")
	public void update(Model model) {
		logger.info("member/update의 실행 결과 리턴");
		if(session.getAttribute("id") == null || session.getAttribute("id")=="") {
			model.addAttribute("msg", "로그인 정보 없음");
		}
	}	
	@PostMapping(value = "member/update")
	public String update(MemberVO member, Model model) {
		logger.info("post member/update의 실행 결과 리턴");
		switch (memberService.updateOne(member)) {
		case 0:
			model.addAttribute("msg", "수정 이상 발생");
			return "member/update";
		case 1:
			model.addAttribute("msg", "수정 완료");
			session.invalidate();
			return "member/login";
		case 2:
			model.addAttribute("msg", "등록 정보 없음");
			return "member/update";
			
		default:
			model.addAttribute("msg", "비밀번호 미 일치");
			return "member/update";
		}
		
	}	
	@GetMapping(value = "member/delete")
	public void delete(Model model) {
		logger.info("member/delete의 실행 결과 리턴");
		if(session.getAttribute("id") == null || session.getAttribute("id")=="") {
			model.addAttribute("msg", "로그인 정보 없음");
		}
	}	
	@PostMapping(value = "member/delete")
	public String delete(MemberVO member, Model model) {
		logger.info("post member/delete의 실행 결과 리턴");
		switch (memberService.deleteOne(member)) {
		case 0:
			model.addAttribute("msg", "이상 발생");
			return "member/delete";
		case 1:
			model.addAttribute("msg", "삭제 완료");
			session.invalidate();
			return "member/index";
		case 2:
			model.addAttribute("msg", "등록 정보 없음");
			return "member/delete";
			
		default:
			model.addAttribute("msg", "비밀번호 미 일치");
			return "member/delete";
		}
		
	}	
	
	//카카오 인증을 통해 돌아온 리다이랙트 uri 값을 수신하기 위한 함수
	//인가 받은 코드(예시) :http://localhost:8085/spring_db/member/kakaoLogin?code=De_2cIzGO33C5Yb3jmeuU8kxvuRIaKH2VfIkEd1O7jBJWHQvKvm2Tlgw4K-JvuUgVAfOzAopyNgAAAGAaajk5w
	@RequestMapping("member/kakaoLogin")
	public String kakaoLogin(String code) {
		System.out.println("카카오 인가 코드 : "+ code);
		
		return "member/index";
	}
	
}

