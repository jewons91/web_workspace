<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 정보 조회</title>
</head>
<body>
	<h2>상품 상세 정보</h2>
	<hr/>
	<ul>
		<li>상품코드 : ${product.id }</li>
		<li>상품명 : ${product.name }</li>
		<li>제조사 : ${product.maker }</li>
		<li>가격 : ${product.price }</li>
		<li>제조일 : ${product.date }</li>
	</ul>
</body>
</html>