<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex4</title>
</head>
<!-- Ajax를 이용해서 외부로 데이터 보내기 -->
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
		req.open('post','ex4');
		var i = document.getElementById('id').value;
		var p = document.getElementById('pw').value;
		data = {id:i, pw:p}//JSON형식의 데이터 만들기 -> java형태로 보면 ("id",i) 즉 {식별자1: 데이터1, 식별자2: 데이터2}
		jdata = JSON.stringify(data);//JSON형식의 데이터 입력하기
		req.setRequestHeader('Content-Type','application/json; charset=utf-8');//보내고자 하는 데이터의 자료형을 알려주는것
		//컨테이너에 보내고자하는 형식을 알려준다.
		req.send(jdata);
	}
	function textChang(){
		if(req.readyState == 4 && req.status == 200) {
			document.getElementById('msg').innerHTML = req.responseText;
		}
	
	}
	
</script>
<body>
	<label id="msg"></label><br><!-- 보낸 데이터 확인용 -->
	아이디 : <input type="text" id="id"/><br>
	비밀번호 : <input type="text" id="pw"/><br>
	<button id="button" type="button">정보 전달하기</button>
</body>
</html>