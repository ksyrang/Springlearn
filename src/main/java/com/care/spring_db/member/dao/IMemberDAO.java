package com.care.spring_db.member.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.care.spring_db.member.MemberDTO;


@Repository
public interface IMemberDAO {
	//추상메소드 : spring(hikari+ mybatis)는 추상메소드만 올리고 내용은 mapper.xml에 작성 
	int insert(MemberDTO Member);
	
	MemberDTO selectId(String id);
	
	int updateMem(MemberDTO member);
	
	int deleteMem(MemberDTO member);
	
	ArrayList<MemberDTO> selectAll();
	
}
