<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ex6</title>
</head>
<!-- json 확장자 파일안 데이터 집합을 가져오기 -->
<!-- DB에서 가져오는 것이 아니면서 새로고침없디 리스트를 빠르게 불러들일 수 있다. (Ex: 받은 이메일 리스트 표시 할때) -->
<!-- 불러온 리스트를 검색 하는 방법 -->
<script>
	window.onload= start;
	
	function start(){
		send();//시작하자마다 데이터를 요청하여 리스트를 출력하기 위함
		var button = document.getElementById('button');//동작을 제어할 오브젝트
		button.onclick=send;//특정 조건을 가진 상태의 리스트를 출력하기 위함
	}
	
	var req;
	function send(){//데이터 송신 메소드
		//alert('onload start');
		req = new XMLHttpRequest();
		req.onreadystatechange = textChang;
		req.open('post','ex6');
		req.send(document.getElementById('title').value);//필터링 하고 싶은 데이터를 JAX 메소드를 이용하여 송신
	}
	function textChang(){//데이터 수신 메소드
		if(req.readyState == 4 && req.status == 200) {
			jDatas = JSON.parse(req.responseText);//json확장자의 파일에서 가져오 데이터 뭉치
			
			data = "";
			for(i=0;i<jDatas.cd.length;i++){//jDatas.length = 대집합의 개수(1개), jDatas.cd.length = cd안에 있는 라인 수
				data = data + "<tr>";//행 선언
				data = data + "<td>"+jDatas.cd[i].title+"</td>";//열에 데이터 입력
				data = data + "<td>"+jDatas.cd[i].artist+"</td>";//열에 데이터 입력
				data = data + "<td>"+jDatas.cd[i].price+"</td>";//열에 데이터 입력
				data = data + "</td>";//행 선언 닫기
			}
			var tbody = document.getElementById('tbody');//테이블 태그 안 tbody의 객체 정보
			tbody.innerHTML = data; // 데이터 입력!
		}
	
	}
	
</script>
<body>

<input type="text" id="title"/>
	<button id="button" type="button">정보 가져하기</button>
	<table border=1>
		<thead>
			<tr>
				<th style="background-color: #EAEAEA">title</th>
				<th style="background-color: #EAEAEA">artist</th>
				<th style="background-color: #EAEAEA">price</th>
			</tr>
		</thead>
		<tbody id="tbody">
		
		</tbody>
	</table>
</body>
</html>
