<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex3</title>
</head>
<!-- Ajax를 이용해서 외부 데이터 가져오기(받기만 하기) -->
<script>
	window.onload= start;
	
	function start(){
		var button = document.getElementById('button');//동작을 제어할 오브젝트
		button.onclick=send;
	}
	
	var req;
	function send(){
		//alert('onload start');
		req = new XMLHttpRequest();
		req.onreadystatechange = textChang;
		req.open('post','ex3');
		req.send(null);
	}
	function textChang(){
		if(req.readyState == 4 && req.status == 200) {
			//여러개의 데이터를 받을때 map 자료형을 이용 할 수 있다. 다른 방법도 가능 할 것
			jsonData = JSON.parse(req.responseText);//JSON 객체에 parse라는 메소드를 이용하여 map형과 같은 다량의 데이터를 받을 때 사용
			//jsson형식으로 데이터를 바아서
			var id = document.getElementById('id');
			id.innerHTML = jsonData.id;;//json의 형식으로 데이터를 불러옴
			var pw = document.getElementById('pw');
			pw.innerHTML = jsonData.pw;//받은 데이터들을 구분지어 줄때 .id = 보낼때 사용한 객체 식별자
			//JSON은 key,value로 구성되어 있다. == map 메소드와 비슷한 구조
		}
	
	}
	
</script>
<body>
	아이디 : <label id="id"></label><br>
	비밀번호 : <label id="pw"></label><br>
	<button id="button" type="button">정보 가져오기</button>
</body>
</html>