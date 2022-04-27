package com.care.spring_db.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

//@Repository, @Service 모두 스프링 컨테이너에 넣어서 @Autowired를 이요하여 불러들여 사용 한다.
//@Repository //DAO를 스프링에서 저장소라고 정의하고 사용토록하기 위한 언옵테이션 -> 서비스클래스에서 @Autowired로 불러올 수 있음.
public class MemberDAO {
	
	private Connection con;
	
	
	public MemberDAO() {
		String url= "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "spdb";
		String password = "spdb1";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int insert(MemberDTO member) {
		PreparedStatement ps;
		int result = 0;
		String sql = "INSERT INTO basic VALUES(?,?,?,?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, member.getId());
			ps.setString(2, member.getPw());
			ps.setString(3, member.getName());
			ps.setString(4, member.getEmail());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}//ist end
	
	public MemberDTO selsectId(String id) {
		PreparedStatement ps;
		ResultSet rs;
		MemberDTO tmp = new MemberDTO();
		String sql = "SELECT * FROM basic WHERE id=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println(id);
				tmp.setId(rs.getString("id"));
				tmp.setPw(rs.getString("pw"));
				tmp.setName(rs.getString("name"));
				tmp.setEmail(rs.getString("email"));
				return tmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}//slt end

	public int updateMem(MemberDTO member) {
		PreparedStatement ps;
		int result = 0;
		String sql = "UPDATE basic SET pw=?,name=?,email=? WHERE id=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, member.getPw());
			ps.setString(2, member.getName());
			ps.setString(3, member.getEmail());
			ps.setString(4, member.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}//udt end
	
	public int deleteMem(MemberDTO member) {
		PreparedStatement ps;
		int result = 0;
		String sql = "DELETE FROM basic WHERE id=?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, member.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}//dlt end
	
	public ArrayList<MemberDTO> selsetAll() {
		PreparedStatement ps;
		ResultSet rs;
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		String sql = "SELECT * FROM basic";
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				MemberDTO tmp = new MemberDTO();
				tmp.setId(rs.getString("id"));
				tmp.setPw(rs.getString("pw"));
				tmp.setName(rs.getString("name"));
				tmp.setEmail(rs.getString("email"));
				list.add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;	
	}//sltAll end
}
