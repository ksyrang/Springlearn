<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
</head>
<body>
<c:if test="${not empty sessionScope.id }">
	<script>location.href="index";</script>
</c:if>
<c:if test="${not empty msg }">
	<script>alert("${msg}");</script>
</c:if>

	<form action="login" method="post" id="f"><!-- 같은 명칭을 가진 이름이 index에 있지만 보내는 메소드가 다름 -->
		<input type="text" name="id"  placeholder="아이디"><br>
		<input type="password" name="pw"  placeholder="비밀번호"><br>
		<input type="button" value="회원 가입" onclick="check();">
		<input type="button" value="취소" onclick="javacript:location.href='index'">
	</form>
	<!-- 카카오 인증을 요청하기 위한 코드 -->
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=e212287b21c4699882de4833ad354ee5&redirect_uri=http://localhost:8085/spring_db/member/kakaoLogin&response_type=code">
	<%-- <a href="https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code HTTP/1.1"> --%>
	<!-- 주소로 api값과 되돌아올 uri 값을 넣어줘야 한다. -->
		<img src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg" width="150px">
	</a>
</body>
</html>
<script>
	function check(){
/* 		id = document.getElementById('id');
		pw = document.getElementById('pw');
 		if(id.value == "" || pw.value == ""){
			alert('필수 항목입니다.');
			return;
		}*/
		document.getElementById('f').submit();
	}
</script>
