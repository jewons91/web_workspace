<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>계산기 서블릿</title>
</head>
<body>
	<h2>계산기 서블릿</h2>
	<hr>
	<form method="post" action="/jwbook/calc">
		<input type="text" name="n1" size="10"> 
		<select name="op">
			<option>+</option>
			<option>-</option>
			<option>*</option>
			<option>/</option>
		</select> 
		<input type="text" name="n2" size="10"> 
		<input type="submit" value="실행">
	</form>
</body>
</html>
