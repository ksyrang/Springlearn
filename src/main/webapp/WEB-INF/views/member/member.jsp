<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member</title>
</head>
<script>
	
	var req;
	function send(){
		//alert('onload start');
		req = new XMLHttpRequest();
		req.onreadystatechange = textChang;//onreadystatechange : XMLHttp의 상태가 변경이되면 오른쪽 메소드를 동작 시켜라
		//즉 비동기형식으로 상태 변화가 될떄마다 오른쪽 함수를 실행 시키며 함수는 특정 조건이 만족되면 내용을 실행해라!
		req.open('post','idcheck');
		var id = document.getElementById('id').value;
		req.send(id);//데이터를 보낸 것 문자열로 예시로 송부
	}
	function textChang(){
		if(req.readyState == 4 && req.status == 200) {
			var data = document.getElementById('display');
			data.innerHTML = req.responseText;
		}
	
	}
	//이메일 인증용
	function sendAuth(){
		req = new XMLHttpRequest();
		req.onreadystatechange = mailsendresult;//onreadystatechange : XMLHttp의 상태가 변경이되면 오른쪽 메소드를 동작 시켜라
		//즉 비동기형식으로 상태 변화가 될떄마다 오른쪽 함수를 실행 시키며 함수는 특정 조건이 만족되면 내용을 실행해라!
		req.open('post','sendAuth');
		var id = document.getElementById('email').value;
		req.send(id);//데이터를 보낸 것 문자열로 예시로 송부
	}
	function mailsendresult(){
		if(req.readyState == 4 && req.status == 200) {
			var mailreceive = document.getElementById('mailsendDis');
			mailreceive.innerHTML = req.responseText;
		}
	
	}
	
	function checkAuth(){
		req = new XMLHttpRequest();
		req.onreadystatechange = mailresult;//onreadystatechange : XMLHttp의 상태가 변경이되면 오른쪽 메소드를 동작 시켜라
		//즉 비동기형식으로 상태 변화가 될떄마다 오른쪽 함수를 실행 시키며 함수는 특정 조건이 만족되면 내용을 실행해라!
		req.open('post','checkAuth');
		req.setRequestHeader('Content-Type','application/json; charset=utf-8');
		//보내고자 하는 데이터의 자료형을 알려주는것
		//컨테이너에게 보내고자하는 형식을 알려준다.
		
		var check_num = document.getElementById('num_check').value;
		var data = {check_num: check_num};
		senddata = JSON.stringify(data);
		req.send(senddata);//데이터를 보낸 것 문자열로 예시로 송부	
	}
	function mailresult(){
		if(req.readyState == 4 && req.status == 200) {
			var mailreceive = document.getElementById('mailreceiveDis');
			mailreceive.innerHTML = req.responseText;
		}
	
	}

	function check(){
		
/* 		id = document.getElementById('id');
		pw = document.getElementById('pw');
		confirmPw = document.getElementById('confirmPw');
 		if(id.value == "" || pw.value == "" || document.getElementById('name').value == ""){
			alert('필수 항목입니다.');
			return;
		}
		if(pw.value != confirmPw.value){
			alert('두 비밀번호가 일치하지 않습니다.');
			return;
		}  */
		document.getElementById('f').submit();
	}

	
</script>
<body>
<c:if test="${not empty msg }">
	<script>alert("${msg}");</script>
</c:if>
<h1>회원 가입</h1>

<form action="member" method="post" id="f"><!-- 같은 명칭을 가진 이름이 index에 있지만 보내는 메소드가 다름 -->
	<input type="text" id="id" name="id"  placeholder="아이디">
	<input type="button" id="link" value="아이디 확인" onclick="send();"><br>
	<label id="display"></label><br>
	<input type="password" name="pw"  placeholder="비밀번호"><br>
	<input type="password" name="confirmPw"  placeholder="비밀번호확인"><br>
	<input type="text" name="name"  placeholder="이름"><br>
	<input type="text" name="email" id="email" placeholder="email">
	<input type="button" id="email_link" value="인증번호 전송" onclick="sendAuth();"><br>
	<label id="mailsendDis"></label><br>
	<input type="text" name="num_check" id="num_check" placeholder="num_check">
	<input type="button" id="check_num_btn" value="인증번호 확인" onclick="checkAuth();"><br>
	<label id="mailreceiveDis"></label><br>
	<input type="button" value="회원 가입" onclick="check();">
	<input type="button" value="취소" onclick="javacript:location.href='index'">
</form>

</body>
</html>

