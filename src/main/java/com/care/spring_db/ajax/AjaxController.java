package com.care.spring_db.ajax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//AJAX 방식을 이용한 데이서 송부! 제이 쿼리 라이브러리 이용!)
@Controller
public class AjaxController {
	
	@GetMapping("ex1")
	public String ex1() {
		System.out.println("get method ex1");
		return "ajax/ex1";
	}
	
	
	@ResponseBody //보내고자 하는 데이터를 컨트롤러에서 보낼려면 @ResponseBody가 필요
	@PostMapping(value="ex1",produces = "text/html; charset=UTF-8")
	public String ex1post() {
		System.out.println("AJAX 요청 ex1");
		
		return "AJAX 서버의 응답 데이터";//즉 return을 응답 데이터로 송신(not 페이지 오픈)
	}
	
	@GetMapping("ex2")
	public String ex2() {
		System.out.println("get method ex2");
		return "ajax/ex2";
	}
	
	//jsp에서 전달한 데이터를 다시 보내는 것 = echo
	@ResponseBody
	@PostMapping(value="ex2",produces = "text/html; charset=UTF-8")
	public String ex2post(@RequestBody String reqdata) {
		//Ajax(jsp) 이벤트에서 보낸 데이터를 받는 것 -> @RequestBody로 선언해 줘야한다.
		System.out.println("AJAX 요청 ex2");
		return reqdata;
	}
	
	@GetMapping("ex3")
	public String ex3() {
		System.out.println("get method ex3");
		return "ajax/ex3";
	}
	//메이븐에 json용 maven 라이브러리를 다운로드 받아야 한다.
	//받는 데이터가 여러개이며 보내는 데이터도 여러개일 경우 JSON 클래스 사용
	@ResponseBody
	@PostMapping(value="ex3",produces = "application/json; charset=utf-8")
	public Map<String, String> ex3post() {
		Map<String, String> map = new HashMap<String, String>();//hashmap으로 선언하여
		map.put("id", "admin");
		map.put("pw", "1234");
		System.out.println("AJAX 요청 ex3");
		return map;//map형 자료 객체를 보낸다.
	}
	
	@GetMapping("ex4")
	public String ex4() {
		System.out.println("get method ex4");
		return "ajax/ex4";
	}
	@ResponseBody
	@PostMapping(value="ex4",produces = "application/json; charset=utf-8")
	public String ex4post(@RequestBody Map<String, String> map) {//json으로 보낸 데이터를 받기 위한 매개변수 선어
		System.out.println("AJAX 요청 ex4");
		System.out.println("id: "+map.get("id"));
		System.out.println("pw: "+map.get("pw"));
		if("admin".equals(map.get("id")) && "1234".equals(map.get("pw"))) {
			return "로그인 성공";
		}else {
			return "실패";
		}
	}
	
	@GetMapping("ex5")
	public String ex5() {
		System.out.println("get method ex5");
		return "ajax/ex5";
	} 
	//json 확장자 형태의 파일안 데이터를 가져오는 방법
	//해당 방법은 DB에 저장할 필요까지는 없는 데이터들의 집합들을 가져와서 사용할때 유용
	@ResponseBody
	@PostMapping("ex5Json")
	public String ex5Json() throws FileNotFoundException, IOException  {//저장된 json 파일의 내용을 사용하는 것
		System.out.println("Json ex5");
		ClassPathResource resource = new ClassPathResource("ex5.json");//json파일의 경로를 가져와줘
		//이후에 FileReader에서 찾아갈 경로를 가 필요하기 떄문 그 이유는 귀찬게 수작업으로 경로 전체를 쓰기 싫기 때문
		FileReader reader = new FileReader(resource.getFile());
		BufferedReader buffer = new BufferedReader(reader);//파일 리더 중 읽는 속도가 빠른편이다.
		
		String data = "";
		while(true) {
			String tmp = buffer.readLine();//json확장자 파일에 있는 정보를 라인 한개씩 읽어준다.
			
			if(tmp == null) break;//더이상 읽을 정보가 없다면 null값을 가져온다.
			
			data += tmp;//읽어온 값들을 저장한다.
		}
		buffer.close();	
		
		return data;
	}
	@GetMapping("ex6")
	public String ex6() {
		System.out.println("get method ex6");
		return "ajax/ex6";
	} 
	//데이터 필터링를 위한 Json 자료형으로 데이터를 받아 취사 선택을 하겠다.
	@ResponseBody
	@PostMapping("ex6")
	public String ex6Json(@RequestBody (required = false)String title) throws FileNotFoundException, IOException  {
		//@RequestBody는 객체의 참조 데이터가 없으면 안된다 에러 발생, 해당 문제를 해결하기 위한(required = false) 삽입 = 해당 코드는 받는 매개변수안 데이터가 꼭! 있을 필요는 없다(== null)
		System.out.println("Json ex6");
		ClassPathResource resource = new ClassPathResource("ex5.json");
		FileReader reader = new FileReader(resource.getFile());
		System.out.println("title : "+ title);
		
		Gson gson = new Gson();//불러온 Json list내 원하는 객체들만 뽑기위한 라이브러리
		JsonObject obj = gson.fromJson(reader, JsonObject.class);//(불러오고 싶은 FileReader 정보, 내보내고 싶은 객체 자료형)
		
		if(title == null ||title.isEmpty())	return obj.toString();//ex5번과 같이 필터링 없이 리스트 송출
		//즉 ex5의 107~117 라인의 내용이 압축된거.

		JsonArray arr = obj.getAsJsonArray("cd");//집합체 식별명 cd를 JsonArray로 변환해 달라.
		/*{ 
		 *	"cd":[
		 *		{ "title" : "Empire Burlesque", "artist" : "Bob Dylan", "price" : "10.90" },
		 *		{ "title" : "Empire Burlesque", "artist" : "Bob Dylan", "price" : "10.90" },
		 *		{ "title" : "Empire Burlesque", "artist" : "Bob Dylan", "price" : "10.90" }
		 *	]
		 *}
		 */
		String result = "{\"cd\" : [";
		for(int i = 0; i< arr.size();i++) {
			JsonObject tmp = (JsonObject)arr.get(i);//cd안에 있는 한줄을 얻어 낼 수 있다. ex: { "title" : "Empire Burlesque", "artist" : "Bob Dylan", "price" : "10.90" },
			//JsonObject는 JsonArray보다 상위 클래스 이다. 자식 클래스의 정보를 부모 글래스에 넣을려면 강제 형변환이 필요
			String s = tmp.get("title").getAsString();//가져온 데이터를 문자열형태로 변환해 준다.
			boolean check = s.equals(title);
			if(check) {//필터링하여 얻은 값을 Json확장자 파일과 같은 포맷으로 변경하여 jsp에 전달
				result += "{ \"title\" : \""+tmp.get("title").getAsString()+"\",";
				result += " \"artist\" : \""+tmp.get("artist").getAsString()+"\",";
				result += " \"price\" : \""+tmp.get("price").getAsString()+"\" },";
			}
		}
		result = result.substring(0,result.length()-1);//substring (시작값,종료값)사이의 문자만 추출, -> 마지막 ,를 제거 하기 위해
		result += "]}";
		return result;	
	}
	
	//위의 방식으로는 실시간으로 문자열이 포함된 목록을 불러오기가 굉장히 복잡하기 때문에 DB에 넣어 SQL 메소드를 이용하여 처리하려 한다.
	@GetMapping("ex7")
	public String ex7() {
		System.out.println("get method ex7");
		return "ajax/ex7";
	}
	@Autowired private AjaxService service;

	@GetMapping("ex7Insert")
	public String ex7Insert() throws FileNotFoundException, IOException {
		System.out.println("get method ex7Insert");
		service.insert();
		return "ajax/ex7";
	}
	
	@ResponseBody
	@PostMapping("ex7")
	public String ex7Json(@RequestBody (required = false)String searchdata) {
		System.out.println("Post method ex7");
		if(searchdata == null||searchdata.isEmpty()){
			return service.titleAll();
		}else {
			return service.search(searchdata);
		}
	}
	@GetMapping("ex8")
	public String ex8() {
		System.out.println("get method ex8");
		return "ajax/ex8";
	}
	
	@ResponseBody
	@PostMapping("ex8")
	public String ex8Json(@RequestBody (required = false)HashMap<String, String> map) {
		System.out.println("Post method ex8");
		if(map.get("searchData") == null||map.get("searchData").isEmpty()){
			return service.titleAll();
		}else {
			return service.searchFilter(map);
		}
//		if(map.get("searchData") == null||map.get("searchData").isEmpty()){
//			return service.titleAll();
//		}else if(map.get("searchTitle").equals("title")) {
//			return service.search(map.get("searchData"));
//		}else if(map.get("searchTitle").equals("artist")) {
//			return service.searchArtist(map.get("searchData"));
//		}else {
//			return service.searchPrice(map.get("searchData"));
//		}
	}
	
	
}

