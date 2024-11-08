<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="project3/css/contact2.css">
<title>연락처 관리 회원가입</title>
</head>
<body>
	<div id="registerSection" class="row justify-content-center">
		<div class="col-md-6">
			<div class="card shadow-sm">
				<div class="card-body">
					<h2 class="mb-4 text-center">계정 생성</h2>
					<c:if test="${duplicated != null }">
						<div class="alert alert-danger alert-dismissible fade show mt-3">
							${duplicated}
							<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
						</div>
					</c:if>
					<form id="registerForm" action="contact?action=register"
						method="post">
						<div class="mb-3">
							<label for="regUser_id" class="form-label">아이디</label> <input
								type="text" id="regUser_id" name="user_id" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="regPassword" class="form-label">비밀번호</label> <input
								type="password" id="regPassword" name="password"
								class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="regName" class="form-label">이름</label> <input
								type="text" id="regName" name="user_name" class="form-control"
								required>
						</div>
						<button type="submit" class="btn btn-primary w-100">확인</button>
					</form>
					<p class="mt-3 text-center">
						계정이 이미 있으신가요? <a href="contact">로그인하러 가기</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>