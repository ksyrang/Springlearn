<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<!-- 비동기(ajax) 통신 연습 : 페이지의 새로고침(또는 로드) 없이 내부 객체를 수정 또는 변경 하는 방법 -->
<!-- 이러한 동작은 일부분의 변경만이 있기 때문에 서버의 부담을 줄일 수 있다. -->
<script>
	window.onload= start;//페이지가 열리면 start 함수 실행
	
	function start(){
		var link = document.getElementById('link');// id 가 link인 객체 인스턴스
		link.onclick=send;//link의 객체가 눌려지면(onclick) send 함수 동작
		
	}
	
	
	var req;
	function send(){
		//alert('onload start');
		req = new XMLHttpRequest();//비동기 통신을 위한 XMLHttpRequest 객체 인스턴스
		req.onreadystatechange = textChang;//이벤트를 걸었다 textchang함수가 동작 하도록(req의 상태가 변경되면)
			//onreadystatechange = 페이지가 실행되면 내용을 실행 시킨다.
			//요청하고 회신하는 일련의 행위 = 상태(state)
		//컨트롤러에 원하는 메세지를 보내기
		req.open('post','ex1');//전송 메소드, 요청 받을 mapping 주소 = 요청 URL
		req.send(null);//정보를 보낸다.(null = 아직 담아서 보낼 데이터가 없어서 null 데이터가 있다면 소괄호에 넣어서 보내기)
	}
	//비동기 통신 순차 1= 요청,2 = 수신,3= 회신,4 = 회신수진 으로 준비상태 확인
	function textChang(){
		if(req.readyState == 4 && req.status == 200) {	//stateus == 200 -> 페이지 정상 로드	
			var data = document.getElementById('data');// 객체 인스턴스
			data.innerHTML = req.responseText;//응답 받은 데이터를 표시하는 것
		}
	
	}
	
</script>
<body>
	<div id = "data">
		AJAX == 비동기 통신
	</div>
	<a href="#" id="link">데이터 가져오기</a>
</body>
</html>