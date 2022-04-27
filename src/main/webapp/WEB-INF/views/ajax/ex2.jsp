<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex2</title>
</head>
<!-- Ajax에서 req에 데이터 넣어서 controller에 보내기 -->
<script>
	window.onload= start;
	
	function start(){
		var link = document.getElementById('link');
		link.onclick=send;
	}
	
	var req;
	function send(){
		//alert('onload start');
		req = new XMLHttpRequest();
		req.onreadystatechange = textChang;
		req.open('post','ex2');
		var id = document.getElementById('id').value;
		req.send(id);//데이터를 보낸 것 문자열로 예시로 송부
	}
	function textChang(){
		if(req.readyState == 4 && req.status == 200) {
			var data = document.getElementById('data');
			data.innerHTML = req.responseText;
		}
	
	}
	
</script>
<body>
	<input type="text" id="id"/> 
	<div id = "data">
		AJAX == 비동기 통신
	</div>
	<a href="#" id="link">데이터 가져오기</a>
</body>
</html>