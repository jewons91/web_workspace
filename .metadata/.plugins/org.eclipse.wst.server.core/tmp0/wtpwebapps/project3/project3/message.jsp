<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
	crossorigin="anonymous"></script>
<style>
.no-wrap {
	white-space: nowrap;
}

.table{
	vertical-align: middle;
}
</style>
<title>메시지함</title>
</head>
<body>
	<div class="container w-75 mt-5 mx-auto">
		<h2>메시지 목록</h2>
		<hr>
		<table class="table">
			<thead>
				<tr class="no-wrap">
					<th scope="col">보낸이</th>
					<th scope="col">내용</th>
					<th scope="col">보낸 시간</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="message" items="${messages}" varStatus="status">
					<tr>
						<td>${message.from_id}</td>
						<td>${message.content}</td>
						<td class="no-wrap">
							<div>
								<fmt:formatDate value="${message.reg_date}" pattern="yyyy-MM-dd" />
							</div>

							<div>
								<fmt:formatDate value="${message.reg_date}" pattern="HH:mm:ss" />
							</div>
						</td>
						<td class="no-wrap">
							<form action="contact?action=deleteMessage" method="post"
								style="display: inline">
								<input type="hidden" name="message_id"
									value="${message.message_id}">
								<button type="submit" class="btn btn-danger btn-sm">삭제</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<hr>
		<c:if test="${error != null}">
			<div class="alert alert-danger alert-dismissible fade show mt-3">
				에러 발생: ${error}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
		<button class="btn btn-outline-info mb-3" type="button"
			data-bs-toggle="collapse" data-bs-target="#sendForm"
			aria-expanded="false" aria-controls="sendForm">메시지 보내기</button>
		<a class="btn btn-outline-info mb-3"
			href="contact?action=contactList">연락처 목록</a>
		<div class="collapse" id="sendForm">
			<div class="card card-body">
				<form method="post" action="contact?action=insertMessage">
					<input type="hidden" name="from_id" value="${sessionScope.user_id}">
					<label class="form-label">받는 사람</label> <select id="to_message"
						name="to_id" class="form-select">
						<c:forEach var="user" items="${users}" varStatus="status">
							<option value="${user.user_id}">${user.user_id}(${user.user_name})</option>
						</c:forEach>
					</select> <label class="form-label">내용</label>
					<textarea cols="50" rows="5" name="content" class="form-control"></textarea>
					<button type="submit" class="btn btn-success mt-3">보내기</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
