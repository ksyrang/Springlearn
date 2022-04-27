<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
</head>
<body>
	<c:set var="count" value="${0 }"/>
	<c:if test="${not empty list }">
	<table border=1>
		<tr>
			<th>아이디</th>
			<th>비밀번호</th>
			<th>이름</th>
			<th>이메일</th>
		</tr>
		<c:forEach var="member" items="${list }">
			<c:set var="count" value="${count+1 }"/>
		<tr>
			<td>${member.getId() }</td>
			<td>${member.getPw() }</td>
			<td>${member.getName() }</td>
			<td>${member.getEmail() }</td>
		</tr>		
		</c:forEach>
	</table>
	</c:if>
	total Record : ${count }<br>
<a href="index">클를릭하면 인덱스 페이지로!</a>	

</body>
</html>
