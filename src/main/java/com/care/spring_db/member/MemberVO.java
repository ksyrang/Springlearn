package com.care.spring_db.member;


public class MemberVO extends MemberDTO{//DTO 상속
	private String confirmPw;
	private String num_check;

	public String getConfirmPw() {
		return confirmPw;
	}

	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
	}

	public String getNum_check() {
		return num_check;
	}

	public void setNum_check(String num_check) {
		this.num_check = num_check;
	}




}
