<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연락처 관리 로그인</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="project3/css/contact2.css">
</head>
<body>
	<div id="loginSection" class="row justify-content-center">
		<div class="col-md-6">
			<div class="card shadow-sm">
				<div class="card-body">
					<h2 class="mb-4 text-center">로그인</h2>
					<form id="loginForm" action="contact?action=login"
						method="post">
						<div class="mb-3">
							<label for="user_id" class="form-label">아이디</label> <input
								type="text" id="user_id" name="user_id" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="password" class="form-label">비밀번호</label> <input
								type="password" id="password" name="password"
								class="form-control" required>
						</div>
						<button type="submit" class="btn btn-primary w-100">로그인</button>
					</form>
					<p class="mt-3 text-center">
						계정이 없으신가요? <a href="contact?action=toRegister">계정 만들기</a>
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