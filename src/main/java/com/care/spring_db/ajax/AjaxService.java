package com.care.spring_db.ajax;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Service
public class AjaxService {
	@Autowired IAjaxDAO ajaxDAO;
	
	public void insert() throws FileNotFoundException, IOException {//json확장자 파일의 내용을 불러오고 DB넣기 위함.
	
		ClassPathResource resource = new ClassPathResource("ex5.json");
		FileReader reader = new FileReader(resource.getFile());
		
		Gson gson = new Gson();//불러온 Json list내 원하는 객체들만 뽑기위한 라이브러리
		JsonObject obj = gson.fromJson(reader, JsonObject.class);//(불러오고 싶은 FileReader 정보, 내보내고 싶은 객체 자료형)
		
		JsonArray arr = obj.getAsJsonArray("cd");//집합체 식별명 cd를 JsonArray로 변환해 달라.
		
		for(int i = 0; i< arr.size();i++) {
			JsonObject tmp = (JsonObject)arr.get(i);//cd안에 있는 한줄을 얻어 낼 수 있다. ex: { "title" : "Empire Burlesque", "artist" : "Bob Dylan", "price" : "10.90" },
			//JsonObject는 JsonArray보다 상위 클래스 이다. 자식 클래스의 정보를 부모 글래스에 넣을려면 강제 형변환이 필요

			AjaxDTO dto = new AjaxDTO();
			dto.setTitle(tmp.get("title").getAsString());
			dto.setArtist(tmp.get("artist").getAsString());
			dto.setPrice(tmp.get("price").getAsString());

			ajaxDAO.insert(dto);
		}
	}

	private String dataToJason(ArrayList<AjaxDTO> list) {
		String result = "{\"cd\" : [";
		for(AjaxDTO ajax : list) {
			result += "{ \"title\" : \""+ajax.getTitle()+"\",";
			result += " \"artist\" : \""+ajax.getArtist()+"\",";
			result += " \"price\" : \""+ajax.getPrice()+"\" },";
		}
		result = result.substring(0,result.length()-1);
		result += "]}";
		return result;	
	}
	public String titleAll() {
		ArrayList<AjaxDTO> list = ajaxDAO.titleAll();
		String result = dataToJason(list);
		//System.out.println(result);//강사님 혹시 mapper에서 * 안붙여서 그런걸까요?
		return result;
	}
	
	public String search(String searchData) {
		ArrayList<AjaxDTO> list = ajaxDAO.search(searchData);
		String result = dataToJason(list);
		return result;
	}
	public String searchArtist(String searchData) {
		ArrayList<AjaxDTO> list = ajaxDAO.searchArtist(searchData);
		String result = dataToJason(list);
		return result;
	}
	public String searchPrice(String searchData) {
		ArrayList<AjaxDTO> list = ajaxDAO.searchPrice(searchData);
		String result = dataToJason(list);
		return result;
	}
	public String searchFilter(HashMap<String, String> map) {
		ArrayList<AjaxDTO> list = ajaxDAO.searchFilter(map);
		if(list.isEmpty()) return "";
		String result = dataToJason(list);
		return result;
	}
	
	
}
