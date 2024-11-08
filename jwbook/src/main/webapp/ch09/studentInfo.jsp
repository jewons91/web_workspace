<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생정보</title>
</head>
<body>
	<h2>학생정보</h2>
	[<a href="/jwbook/studentControl?action=list">새로고침</a>]
	<hr>
<!-- 목록 출력 시작 -->
	<table border=1>
		<tr>
			<th>학번</th>
			<th>이름</th>
			<th>대학</th>
			<th>생일</th>
			<th>이메일</th>
		</tr>
		<c:forEach var="s" items="${students }">
			<tr>
				<td>${s.stu_id }</td>
				<td>${s.usernm }</td>
				<td>${s.univ }</td>
				<td>${s.birth }</td>
				<td>${s.email }</td>
			</tr>
		</c:forEach>
	</table>
<!-- 목록 출력 끝 -->
<!-- 학생 추가 시작 -->
	<div id="stu_modal">
		<h2>학생 추가</h2>
		<hr>
		<form action="/jwbook/studentControl?action=insert" method="post">
			<label>이름</label>
			<input type="text" name="usernm"><br>
			<label>대학</label>
			<input type="text" name="univ"><br>
			<label>생일</label>
			<input type="text" name="birth"><br>
			<label>이메일</label>
			<input type="email" name="email"><br>
			<button type="submit">등록</button>
		</form>
	</div>
<!-- 학생 추가 끝 -->
</body>
</html>