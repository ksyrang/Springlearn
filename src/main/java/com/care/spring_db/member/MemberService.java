package com.care.spring_db.member;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.care.spring_db.member.dao.IMemberDAO;

//@Repository, @Service 모두 스프링 컨테이너에 넣어서 @Autowired를 이용하여 불러들여 사용 한다.
@Service //스프링에서 서비스로 사용하겠다는 선언인 @Service -> 컨트롤러에서 @Autowired로 불러올 수 있음.
public class MemberService {//객체로 만들어서 스프링쪽에서 제어역전을 시킴 : service 언옵테이션
	@Autowired private IMemberDAO memberDAO;
	@Autowired private HttpSession session;//java에서 세션 사용하기
	
	public int insert(MemberVO memberVO) {
		
		if(memberVO.getId().isEmpty()||memberVO.getPw().isEmpty()) return 2;
//		if(!memberVO.getNum_check().equals((String)session.getAttribute("Auth_Num"))) return 8;
		boolean mailflag = (boolean)session.getAttribute("authState");
		if(!mailflag) return 8;
		if(!memberVO.getPw().equals(memberVO.getConfirmPw())) return 2;
		session.invalidate();
		
		MemberDTO check = memberDAO.selectId(memberVO.getId());
		if(check != null) return 3;
		
		MemberDTO member = new MemberDTO();
		//단방향 암호화 알고리즘 라이브러리
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String securePw = encoder.encode(memberVO.getPw());//입력된 정보를 암호화한다.
		memberVO.setPw(securePw);
		/*
		 * 암호화방식은 SHA-1 60byte의 사이즈를 가지고 있다. 
		 */
		member = memberVO;//부모 클래스는 자식클래스의 데이터를 에러 없이 받을 수 있다.(부모클래스에서 없는 변수는 무시된다)
		//이것이 가장 기본적인 상속의 사용 방법
		return memberDAO.insert(member);
	}//insert end
	
	public int selectId(MemberDTO member) {
		System.out.println(member.getId());
		if(member.getId().isEmpty()||member.getPw().isEmpty()) return 2;
		MemberDTO check = memberDAO.selectId(member.getId());
		if(check == null) return 3;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		/*
		 * 단방향으로 되어있고 랜덤 알고리즘으로 암호화된 코드를 복호화가 불가능 하기 때문에 
		 * 이를 확인하기 위한 함수가 존재 encoder.matches(비교하고자 하는 비밀번호(평문),암호화된 비밀번호)
		 */
		if(encoder.matches(member.getPw(), check.getPw())) {
			//세션 생성
			session.setAttribute("id", check.getId());
			session.setAttribute("name", check.getName());
			session.setAttribute("email", check.getEmail());
			return 1;
		}
		return 0;
	}//selectId end
	
	public MemberDTO selsectOne(String id) {
		if(id.isEmpty()) return null;
		MemberDTO member = memberDAO.selectId(id);
		if(member == null) return null;
		return member;
	}//selectId end
	public boolean checkId(String id) {
		if(id.isEmpty()) return false;
		MemberDTO member = memberDAO.selectId(id);
		if(member == null) return true;
		return false;
	}//selectId end	

	public ArrayList<MemberDTO> sltAll() {
		return memberDAO.selectAll();
	}
	
	public int updateOne(MemberVO memberVo) {
		MemberDTO check = memberDAO.selectId((String)session.getAttribute("id"));
		if(check== null) return 2;
		else if(memberVo.getPw().isEmpty()||memberVo.getConfirmPw().isEmpty()) return 2;
		else if(!memberVo.getPw().equals(memberVo.getConfirmPw())) return 3;
		memberVo.setId((String)session.getAttribute("id"));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String securePw = encoder.encode(memberVo.getPw());
		memberVo.setPw(securePw);
		
		return memberDAO.updateMem(memberVo);
	}

	public int deleteOne(MemberVO memberVo) {
		MemberDTO check = memberDAO.selectId((String)session.getAttribute("id"));
		if(check== null) return 2;
		else if(memberVo.getPw().isEmpty()||memberVo.getConfirmPw().isEmpty()) return 2;
		else if(!memberVo.getPw().equals(memberVo.getConfirmPw())) return 3;
		memberVo.setId((String)session.getAttribute("id"));
		return memberDAO.deleteMem(memberVo);
	}
	

}
