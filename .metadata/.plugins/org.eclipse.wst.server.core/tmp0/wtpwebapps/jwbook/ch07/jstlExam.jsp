<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- JSTL 실습 예제 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>JSTL 종합 예제</title>
</head>
<body>
	<h2>JSTL 종합 예제</h2>
	<!-- set, out -->
	<h3>set, out</h3>
	<!-- java code => String product1 = "<b>애플 아이폰</b>"; -->
	<c:set var="product1" value="<b>애플 아이폰</b>" />
	<c:set var="product2" value="삼성 갤럭시 노트" />
	<!-- java code => int[] intArray = new int[5]{1,2,3,4,5}; -->
	<!-- <c:set var="intArray" value="[1,2,3,4,5]"/> error-->
	<%-- <c:set var="intArray" value="${[1,2,3,4,5]}" /> --%>
	<!-- 배열 생성 대입 -->
	<p>
		product1 : ${product2} EL로 출력
	</p>
	<p>
		intArray[2] : ${intArray[2]} EL로 출력
	</p>
	<h3>forEach : 배열 출력</h3>
	<ul>
		<c:forEach var="k" items="${intArray}">
			<li>${k}</li>
		</c:forEach>
	</ul>
	<h3>if : else 없다</h3>
	<c:set var="checkout" value="false"/>
	<c:if test="${!checkout}">
		<p>
			주문 제품 : ${product2}
		</p>
	</c:if>
	<!-- prodcut2 = null -->
	<c:if test="${!empty product2}">
		<p>
			<b>${product2} 이미 추가됨</b>
		</p>
	</c:if>
	
	<h3>choose, when, otherwise</h3>
	<c:choose>
		<c:when test="${checkout}">
			<p>
				주문제품 : ${product2}
			</p>
		</c:when>
		<c:otherwise>
			<p>
				주문 제품이 아님!
			</p>
		</c:otherwise>
	</c:choose>
</body>
</html>