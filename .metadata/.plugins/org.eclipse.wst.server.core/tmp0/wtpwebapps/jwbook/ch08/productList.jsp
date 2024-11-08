<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	<title>상품 목록</title>
</head>
<body>
	<h2>상품 목록</h2>
	<table class="table">
		  <thead>
		    <tr>
		      <th scope="col">번호</th>
		      <th scope="col">상품명</th>
		      <th scope="col">가격</th>
		    </tr>
		  </thead>
		  <tbody>
		  <!-- 반복 시작 -->
		  <c:forEach var="p" items="${products }">
		    <tr>
		      <th scope="row">${p.id }</th>
		      <td>
		      	<a href="/jwbook/pcontrol?action=info&id=${p.id}">
		      		${p.name }
		      	</a>
		      </td>
		      <td>${p.price }</td>
		    </tr>
		  </c:forEach>
		  <!-- 반복 끝 -->
		  </tbody>
	</table>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous">
	</script>
</body>
</html>